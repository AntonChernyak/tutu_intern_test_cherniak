package com.example.domain.repository

import com.example.domain.model_dto.PokemonDto
import com.example.domain.model_dto.PokemonsResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRemoteRepositoryInterface {

    suspend fun getPokemons(offset: String, limit: String): Flow<PokemonsResponse>

    suspend fun getPokemonByNameOrId(nameOrId: String): Flow<PokemonDto>
}