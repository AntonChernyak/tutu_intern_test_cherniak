package com.example.domain.interactors

import com.example.domain.models.model_vo.PokemonDetailsVo
import com.example.domain.repository.PokemonDetailsLocalRepositoryInterface

class PokemonDetailsInteractor(val detailsLocalRepository: PokemonDetailsLocalRepositoryInterface) {

    suspend fun getPokemonByNameOrId(pokemonName: String): PokemonDetailsVo{
        return detailsLocalRepository.getPokemonDetails(pokemonName)
    }
}