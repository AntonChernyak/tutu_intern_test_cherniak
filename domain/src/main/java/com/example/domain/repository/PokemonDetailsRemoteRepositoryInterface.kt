package com.example.domain.repository

import com.example.domain.models.model_vo.PokemonDetailsModelVo

interface PokemonDetailsRemoteRepositoryInterface {

    suspend fun getPokemonDetailsDto(pokemonName: String) : PokemonDetailsModelVo
}