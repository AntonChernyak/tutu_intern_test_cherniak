package com.example.domain.interactors

import com.example.domain.models.mapper.PokemonDetailsDtoToListItemVoMapper
import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.domain.repository.PokemonListLocalRepositoryInterface
import com.example.domain.repository.PokemonListRemoteRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonsListInteractor(
    val remoteRepository: PokemonListRemoteRepositoryInterface,
    val pokemonLocalRepository: PokemonListLocalRepositoryInterface,
    val pokemonDetailDtoToListItemVoMapper: PokemonDetailsDtoToListItemVoMapper,
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

    // Или ListAdapter, или как-нибудь AsyncLisDiffer или PagingLibrary

    // context в даггер по нормальному передать, убрать depricated

    // возможно, убрать запрос из БД по нескольким полям, всё равно мапим, так может 2 маппинга получается? проверить

    // Версии либ в отдельный файл

    // Unit на Usecase с Mockito, Espresso

    // colabsing аватарки на детали

    // Тут одинаковое действие - по енаму, в метод бы

    // Передавать имена и name через аргументы

    private val pokemonVoList = mutableListOf<PokemonListItemModelVo>()
    private var uiStateEnum = UIStateEnum.DEFAULT

    suspend fun getPokemons(offset: String, limit: String): List<PokemonListItemModelVo> {
        pokemonVoList.clear()
        uiStateEnum = UIStateEnum.START_LOADING
        changeUiState()

        val namesList = remoteRepository.getPokemons(offset, limit).results.map { it.name }
        // если сети нет, то грузим из БД
        if (namesList.isEmpty()) {
            uiStateEnum = UIStateEnum.NETWORK_ERROR
            changeUiState()
            pokemonLocalRepository.getPokemonsListWithNamesAndAvatarUrls()
        } else {
            // сеть есть - идём за картинкой к покемону
            namesList.forEach {
                val pokemonDetailsDto = getPokemonByNameOrID(it)
                pokemonVoList.add(pokemonDetailDtoToListItemVoMapper.toOutObject(pokemonDetailsDto))
                //  заодно сразу грузим его в БД
                pokemonLocalRepository.addPokemon(pokemonDetailsDto)
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

    suspend fun getPokemonByNameOrID(nameOrId: String): PokemonDto {
        return remoteRepository.getPokemonByNameOrId(nameOrId)
    }

    fun changeUiState(): Flow<UIStateEnum> {
        return flow { emit(uiStateEnum) }
    }
}