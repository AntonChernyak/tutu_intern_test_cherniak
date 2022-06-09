package com.example.domain.repository

import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.PokemonListItemModelVo

interface PokemonListLocalRepositoryInterface {

    suspend fun addPokemon(pokemonDetailsDto: PokemonDto)

    suspend fun getPokemonsListWithNamesAndAvatarUrls(
        offset: String, limit: String
    ): List<PokemonListItemModelVo>

    suspend fun clearDatabase()
}