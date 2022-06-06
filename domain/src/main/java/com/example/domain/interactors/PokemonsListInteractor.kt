package com.example.domain.interactors

import com.example.domain.models.mapper.PokemonDetailsDtoToListItemVoMapper
import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.PokemonListData
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.domain.repository.PokemonListLocalRepositoryInterface
import com.example.domain.repository.PokemonListRemoteRepositoryInterface
import kotlinx.coroutines.flow.*

class PokemonsListInteractor(
    private val remoteRepository: PokemonListRemoteRepositoryInterface,
    private val pokemonLocalRepository: PokemonListLocalRepositoryInterface,
    private val pokemonDetailDtoToListItemVoMapper: PokemonDetailsDtoToListItemVoMapper,
) {

    // Найти картинки для Деталей

    // выделить методы из этого огромного

    // Использовать Dagger, сначала только там, где создаются зависимости, потом в Дата и Домейн

    // Subcomponent для VM

    // Проверка сети

    // Передавать названия и данные через NavComponent

    // при запуске если есть сеть, псоле загрузки данных чистить БД

    // запрос на новый getItems делать если есть сеть, иначе он из БД добавит

    // Или ListAdapter, или как-нибудь AsyncLisDiffer или PagingLibrary

    // ConcatAdapter или ещё что

    // context в даггер по нормальному передать, убрать depricated

    // Unit на Usecase с Mockito, Espresso

    // colabsing аватарки на детали

    // Тут одинаковое действие - по енаму, в метод бы

    // Передавать имена и name через аргументы

    // shimmer

    // Версии либ в отдельный файл

    private val pokemonVoList = mutableListOf<PokemonListItemModelVo>()
    private val mUiStateMutableFlow = MutableStateFlow(UIStateEnum.DEFAULT)
    val uiStateFlow : StateFlow<UIStateEnum> = mUiStateMutableFlow

    private fun getOffsetString(url: String?): String? {
        return url?.substringAfter("=")?.substringBefore("&")
    }
    private fun getLimitString(url: String?): String {
        return url?.substringAfterLast("=") ?: "0"
    }

    suspend fun getPokemons(offset: String, limit: String): PokemonListData {
        pokemonVoList.clear()
        mUiStateMutableFlow.value = UIStateEnum.START_LOADING

        val pokemonResponse = remoteRepository.getPokemons(offset, limit)
        val pOffset = getOffsetString(pokemonResponse.previous)
        val nOffset = getOffsetString(pokemonResponse.next)
        val pLimit = getLimitString(pokemonResponse.previous)
        val nLimit = getLimitString(pokemonResponse.next)
        val namesList = pokemonResponse.results.map { it.name }
        // если сети нет, то грузим из БД
        if (namesList.isEmpty()) {
            mUiStateMutableFlow.value = UIStateEnum.NETWORK_ERROR
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
        mUiStateMutableFlow.value = UIStateEnum.END_LOADING
        if (pokemonVoList.isEmpty()) {
            mUiStateMutableFlow.value = UIStateEnum.DATA_NOT_FOUND
        }
        return PokemonListData(
            previousOffset = pOffset,
            nextOffset = nOffset,
            itemListVo = pokemonVoList,
            nextLimit = nLimit,
            previousLimit = pLimit
        )
    }

    suspend fun getPokemonByNameOrID(nameOrId: String): PokemonDto {
        return remoteRepository.getPokemonByNameOrId(nameOrId)
    }
}