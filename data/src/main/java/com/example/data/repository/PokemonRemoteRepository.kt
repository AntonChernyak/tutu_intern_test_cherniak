package com.example.data.repository

import com.example.data.network.PokemonApiInterface
import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_dto.PokemonsResponse
import com.example.domain.repository.PokemonListRemoteRepositoryInterface

class PokemonRemoteRepository(private val pokemonApi: PokemonApiInterface) : PokemonListRemoteRepositoryInterface {

    override suspend fun getPokemons(offset: String, limit: String): PokemonsResponse {
        return pokemonApi.getPokemons(offset, limit)
    }

    override suspend fun getPokemonByNameOrId(nameOrId: String): PokemonDto {
        return pokemonApi.getPokemonByNameOrId(nameOrId)
    }

}