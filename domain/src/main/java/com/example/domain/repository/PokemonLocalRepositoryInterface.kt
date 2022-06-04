package com.example.domain.repository

import com.example.domain.models.model_vo.PokemonDetailsVo
import com.example.domain.models.model_vo.PokemonListVo

interface PokemonLocalRepositoryInterface {

    suspend fun addPokemon(pokemonDetailsVo: PokemonDetailsVo)

    suspend fun getPokemonsListWithNamesAndAvatarUrls(): List<PokemonListVo>
}