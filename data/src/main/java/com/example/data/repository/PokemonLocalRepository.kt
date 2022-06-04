package com.example.data.repository

import com.example.data.database.PokemonDao
import com.example.domain.models.model_vo.PokemonDetailsVo
import com.example.domain.models.model_vo.PokemonListVo
import com.example.domain.repository.PokemonLocalRepositoryInterface

class PokemonLocalRepository(val dao: PokemonDao): PokemonLocalRepositoryInterface {
    override suspend fun addPokemon(pokemonDetailsVo: PokemonDetailsVo) {
        dao.addPokemonToDb(pokemonDetailsVo)
    }

    override suspend fun getPokemonsListWithNamesAndAvatarUrls(): List<PokemonListVo> {
        return dao.getPokemonsListWithNamesAndAvatarUrls()
    }
}