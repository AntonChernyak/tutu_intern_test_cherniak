package com.example.data.repository

import com.example.data.mapper.PokemonDetailsDtoToDetailsVoMapper
import com.example.data.network.PokemonApiInterface
import com.example.domain.models.model_vo.PokemonDetailsModelVo
import com.example.domain.repository.PokemonDetailsRemoteRepositoryInterface
import javax.inject.Inject

class PokemonDetailsRemoteRepository @Inject constructor(
    private val pokemonApiInterface: PokemonApiInterface,
    private val pokemonDetailsDtoToDetailsVoMapper: PokemonDetailsDtoToDetailsVoMapper
) : PokemonDetailsRemoteRepositoryInterface {

    override suspend fun getPokemonDetailsDto(pokemonName: String): PokemonDetailsModelVo {
        return pokemonDetailsDtoToDetailsVoMapper.toOutObject(
            pokemonApiInterface.getPokemonByNameOrId(pokemonName)
        )
    }

}