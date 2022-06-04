package com.example.domain.repository

import com.example.domain.models.model_vo.PokemonDetailsVo

interface PokemonDetailsLocalRepositoryInterface {

    suspend fun getPokemonDetails(pokemonName: String): PokemonDetailsVo
}