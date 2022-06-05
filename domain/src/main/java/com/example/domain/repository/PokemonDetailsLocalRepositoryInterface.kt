package com.example.domain.repository

import com.example.domain.models.model_vo.PokemonDetailsModelVo

interface PokemonDetailsLocalRepositoryInterface {

    suspend fun getPokemonDetails(pokemonName: String): PokemonDetailsModelVo
}