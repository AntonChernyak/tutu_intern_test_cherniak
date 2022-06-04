package com.example.data.network

import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_dto.PokemonsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiInterface {

    @GET("pokemon")
    suspend fun getPokemons(@Query("offset") offset: String, @Query("limit") limit: String): PokemonsResponse

    @GET("pokemon/{name_or_id}")
    suspend fun getPokemonByNameOrId(@Path("name_or_id") name_or_id: String): PokemonDto
}