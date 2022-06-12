package com.example.domain.interactors

import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.domain.repository.PokemonDetailsLocalRepositoryInterface
import com.example.domain.repository.PokemonDetailsRemoteRepositoryInterface

class PokemonDetailsInteractor(
    private val detailsLocalRepository: PokemonDetailsLocalRepositoryInterface,
    private val detailsRemoteRepository: PokemonDetailsRemoteRepositoryInterface
    ) {

    suspend fun getPokemonByNameOrId(pokemonName: String): PokemonDetailsModelVo {
        return try {
            detailsLocalRepository.getPokemonDetails(pokemonName)
        } catch (e: Exception) {
            detailsRemoteRepository.getPokemonDetailsDto(pokemonName)
        }
    }
}
