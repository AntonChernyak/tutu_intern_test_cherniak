package com.example.domain.interactors

import com.example.domain.mapper.PokemonDetailsDtoToVoMapper
import com.example.domain.mapper.PokemonListItemMapper
import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_dto.PokemonsResponse
import com.example.domain.models.model_vo.PokemonDetailsVo
import com.example.domain.models.model_vo.PokemonListVo
import com.example.domain.repository.PokemonLocalRepositoryInterface
import com.example.domain.repository.PokemonRemoteRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PokemonsListInteractor(
    val remoteRepository: PokemonRemoteRepositoryInterface,
    val pokemonLocalRepository: PokemonLocalRepositoryInterface,
    val pokemonListItemMapper: PokemonListItemMapper,
    val pokemonDetailsDtoToVoMapper: PokemonDetailsDtoToVoMapper
) {

    // mb Flow<VO> - mapper tut? Проверить какой поток тут
    // Всё гразим в БД в фоне, а из экрана с детали - запрос БД
    // Если список не пустой, то сохраняем в БД, если сети нет, то из него. Есть - чистим БД, сохраняем новые

    // Нужно тут сделать Enum - его в VM. Если список пустой и после БД, то через Enum отпарвить состояние, что
    // нужно отобразить плашку - добавить её и loader (shimmer). Также сразу добавить Toolbar

    // Найти картинки для Деталей

    // Использовать Dagger, сначала только там, где создаются зависимости, потом в Дата и Домейн

    // при запуске если есть сеть, псоле загрузки данных чистить БД

    // запрос на новый getItems делать если есть сеть, иначе он из БД добавит

    // Unit на Usecase с Mockito, Espresso

    // Тут одинаковое действие - по енаму, в метод бы
    private val pokemonVoList = mutableListOf<PokemonListVo>()
    private var uiStateEnum = UIStateEnum.DEFAULT

    suspend fun getPokemons(offset: String, limit: String): List<PokemonListVo> {
        pokemonVoList.clear()
        uiStateEnum = UIStateEnum.START_LOADING
        changeUiState()

        val namesList = remoteRepository.getPokemons(offset, limit).results.map { it.name }
        // если сети нет, то грузим из БД
        if (namesList.isEmpty()) {
            uiStateEnum= UIStateEnum.NETWORK_ERROR
            changeUiState()
            pokemonLocalRepository.getPokemonsListWithNamesAndAvatarUrls()
        } else {
            // сеть есть - идём за картинкой к покемону
            namesList.forEach {
                val pokemonDetailsVo = getPokemonByNameOrID(it)
                pokemonVoList.add(pokemonListItemMapper.toViewObject(pokemonDetailsVo))
                //  заодно сразу грузим его в БД
                pokemonLocalRepository.addPokemon(pokemonDetailsVo)
            }
        }
        uiStateEnum = UIStateEnum.END_LOADING
        changeUiState()
        if (pokemonVoList.isEmpty()) {
            uiStateEnum = UIStateEnum.DATA_NOT_FOUND
            changeUiState()
        }
        return pokemonVoList
    }

    suspend fun getPokemonByNameOrID(nameOrId: String): PokemonDetailsVo {
        return pokemonDetailsDtoToVoMapper
            .toViewObject(remoteRepository.getPokemonByNameOrId(nameOrId))
    }

    fun changeUiState(): Flow<UIStateEnum> {
        return flow { emit(uiStateEnum) }
    }
}