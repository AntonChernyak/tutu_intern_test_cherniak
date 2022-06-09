package com.example.data.mapper

import com.example.data.database.model_db.PokemonDetailsModelDb
import com.example.domain.models.mapper.MapperInterface
import com.example.domain.models.model_dto.PokemonDto

class PokemonDetailsDtoToDetailsDbMapper : MapperInterface<PokemonDetailsModelDb, PokemonDto> {

    override fun toOutObject(inObject: PokemonDto): PokemonDetailsModelDb {
        val abilitiesVo = inObject.abilities.map { it.ability.name}
        val formsVo = inObject.forms.map { it.name }
        val statsVo = inObject.stats.map { "${it.stat.name} = ${it.baseStat}"}

        return with(inObject) {
            PokemonDetailsModelDb(
                id = id,
                pokemonName = name,
                pokemonAvatarUrl = sprites.frontDefaultUrl,
                weight = weight,
                height = height,
                abilities = abilitiesVo,
                forms = formsVo,
                stats = statsVo
            )
        }
    }
}