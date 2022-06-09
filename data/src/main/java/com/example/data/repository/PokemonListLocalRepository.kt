package com.example.data.repository

import com.example.data.database.PokemonDao
import com.example.data.mapper.PokemonDetailsDtoToDetailsDbMapper
import com.example.data.mapper.PokemonListItemModelDbToListItemModelVoMapper
import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.PokemonListItemModelVo
import com.example.domain.repository.PokemonListLocalRepositoryInterface
import javax.inject.Inject

class PokemonListLocalRepository @Inject constructor(
    private val dao: PokemonDao,
    private val detailsDtoToDetailsDbMapper: PokemonDetailsDtoToDetailsDbMapper,
    private val pokemonListItemDbToListItemVo: PokemonListItemModelDbToListItemModelVoMapper
) : PokemonListLocalRepositoryInterface {

    override suspend fun addPokemon(pokemonDetailsDto: PokemonDto) {
        dao.addPokemonToDb(detailsDtoToDetailsDbMapper.toOutObject(pokemonDetailsDto))
    }

    override suspend fun getPokemonsListWithNamesAndAvatarUrls(
        offset: String, limit: String
    ): List<PokemonListItemModelVo> {
        return pokemonListItemDbToListItemVo.toOutObject(
            dao.getPokemonsListWithNamesAndAvatarUrls(offset, limit)
        )
    }

    override suspend fun clearDatabase() {
        dao.clearDatabase()
    }
}