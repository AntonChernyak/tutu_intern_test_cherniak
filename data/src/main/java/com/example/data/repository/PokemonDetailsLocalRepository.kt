package com.example.data.repository

import com.example.data.database.PokemonDao
import com.example.data.mapper.PokemonDetailsDbToDetailsVoMapper
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.domain.repository.PokemonDetailsLocalRepositoryInterface
import javax.inject.Inject

class PokemonDetailsLocalRepository @Inject constructor(
    private val dao: PokemonDao,
    private val detailsDbToDetailsVoMapper: PokemonDetailsDbToDetailsVoMapper
) : PokemonDetailsLocalRepositoryInterface {

    override suspend fun getPokemonDetails(pokemonName: String): PokemonDetailsModelVo {
        return detailsDbToDetailsVoMapper.toOutObject(dao.getPokemonDetailsFromDb(pokemonName))
    }
}