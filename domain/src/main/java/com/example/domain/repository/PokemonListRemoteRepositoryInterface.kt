package com.example.domain.repository

import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_dto.PokemonsResponse

interface PokemonListRemoteRepositoryInterface {

    suspend fun getPokemons(offset: String, limit: String): PokemonsResponse

    suspend fun getPokemonByNameOrId(nameOrId: String): PokemonDto
}