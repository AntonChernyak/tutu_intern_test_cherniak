package com.example.data.mapper

import com.example.data.database.model_db.PokemonDetailsModelDb
import com.example.domain.models.mapper.MapperInterface
import com.example.domain.models.model_dto.PokemonDto

class PokemonDetailsDtoToDetailsDbMapper : MapperInterface<PokemonDetailsModelDb, PokemonDto> {

    override fun toOutObject(inObject: PokemonDto): PokemonDetailsModelDb {
        val abilitiesVo = inObject.abilities
            .flatMap { response -> response.ability }
            .map { it.name }
        val itemsVo = inObject.heldItems.flatMap { it.items }.map { it.name }
        val statsVo = inObject.stats.flatMap { it.stat }.map { it.name }

        return with(inObject) {
            PokemonDetailsModelDb(
                pokemonName = name,
                pokemonAvatarUrl = sprites.frontDefaultUrl,
                weight = weight,
                height = height,
                abilities = abilitiesVo,
                items = itemsVo,
                stats = statsVo
            )
        }
    }
}