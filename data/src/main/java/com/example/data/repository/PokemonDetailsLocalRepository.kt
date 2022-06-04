package com.example.data.repository

import com.example.data.database.PokemonDao
import com.example.domain.models.model_vo.PokemonDetailsVo
import com.example.domain.repository.PokemonDetailsLocalRepositoryInterface

class PokemonDetailsLocalRepository(private val dao: PokemonDao): PokemonDetailsLocalRepositoryInterface {

    override suspend fun getPokemonDetails(pokemonName: String): PokemonDetailsVo {
        return dao.getPokemonDetailsFromDb(pokemonName)
    }
}