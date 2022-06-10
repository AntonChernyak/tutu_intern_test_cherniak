package com.example.domain.interactors

import com.example.domain.models.mapper.PokemonDetailsDtoToListItemVoMapper
import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.domain.repository.PokemonListLocalRepositoryInterface
import com.example.domain.repository.PokemonListRemoteRepositoryInterface
import kotlinx.coroutines.flow.*

class PokemonsListInteractor(
    private val remoteRepository: PokemonListRemoteRepositoryInterface,
    private val pokemonLocalRepository: PokemonListLocalRepositoryInterface,
    private val pokemonDetailDtoToListItemVoMapper: PokemonDetailsDtoToListItemVoMapper,
) {

    // Unit на Usecase с Mockito

    private val mUiStateMutableFlow = MutableStateFlow(UIStateEnum.DEFAULT_STATE)
    val uiStateFlow: StateFlow<UIStateEnum> = mUiStateMutableFlow

    private suspend fun getPokemonByNameOrID(nameOrId: String): PokemonDto {
        return remoteRepository.getPokemonByNameOrId(nameOrId)
    }

    private suspend fun getPokemonListItemsFromDb(
        pokemonVoList: MutableList<PokemonListItemModelVo>,
        offset: String,
        limit: String
    ) {
        try {
            pokemonVoList.addAll(
                pokemonLocalRepository.getPokemonsListWithNamesAndAvatarUrls(offset, limit)
            )
        } catch (e: Exception) {
            error("Database Exception: ${e.localizedMessage}")
        }
    }

    suspend fun getPokemons(offset: String, limit: String): List<PokemonListItemModelVo> {
        val pokemonVoList = mutableListOf<PokemonListItemModelVo>()
        try {
            val pokemonResponse = remoteRepository.getPokemons(offset, limit)
            val namesList = pokemonResponse.results.map { it.name }
            namesList.forEach {
                val pokemonDetailsDto = getPokemonByNameOrID(it)
                pokemonVoList.add(pokemonDetailDtoToListItemVoMapper.toOutObject(pokemonDetailsDto))
                pokemonLocalRepository.addPokemon(pokemonDetailsDto)
            }
            mUiStateMutableFlow.value = UIStateEnum.NETWORK_AVAILABLE
        } catch (e: Exception) {
            getPokemonListItemsFromDb(pokemonVoList, offset, limit)
        }
        return pokemonVoList
    }

    suspend fun clearDatabase(){
        pokemonLocalRepository.clearDatabase()
    }

}