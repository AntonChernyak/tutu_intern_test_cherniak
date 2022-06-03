package com.example.data.repository

import com.example.data.network.PokemonApiInterface
import com.example.domain.model_dto.PokemonDto
import com.example.domain.model_dto.PokemonsResponse
import com.example.domain.repository.PokemonRemoteRepositoryInterface
import kotlinx.coroutines.flow.Flow

class PokemonRemoteRepository(private val pokemonApi: PokemonApiInterface) :

    PokemonRemoteRepositoryInterface {

    override suspend fun getPokemons(offset: String, limit: String): Flow<PokemonsResponse> {
        return pokemonApi.getPokemons(offset, limit)
    }

    override suspend fun getPokemonByNameOrId(nameOrId: String): Flow<PokemonDto> {
        return pokemonApi.getPokemonByNameOrId(nameOrId)
    }

}