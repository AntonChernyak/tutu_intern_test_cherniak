package com.example.domain.interactors

import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.domain.repository.PokemonDetailsLocalRepositoryInterface

class PokemonDetailsInteractor(val detailsLocalRepository: PokemonDetailsLocalRepositoryInterface) {

    suspend fun getPokemonByNameOrId(pokemonName: String): PokemonDetailsModelVo {
        return detailsLocalRepository.getPokemonDetails(pokemonName)
    }
}