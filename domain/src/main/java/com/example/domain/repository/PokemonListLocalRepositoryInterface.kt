package com.example.domain.repository

import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.domain.models.model_vo.PokemonListItemModelVo

interface PokemonListLocalRepositoryInterface {

    suspend fun addPokemon(pokemonDetailsDto: PokemonDto)

    suspend fun getPokemonsListWithNamesAndAvatarUrls(): List<PokemonListItemModelVo>
}