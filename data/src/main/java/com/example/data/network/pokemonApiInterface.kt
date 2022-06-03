package com.example.data.network

import com.example.domain.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Path

interface pokemonApiInterface {

    @GET("pokemon")
    suspend fun getPokemons(): List<PokemonDto>

    @GET("pokemon/{name_or_id}")
    suspend fun getPokemonByNameOrId(@Path("name_or_id") name_or_id: String): PokemonDto
}