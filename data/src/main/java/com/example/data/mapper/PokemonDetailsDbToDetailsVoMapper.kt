package com.example.data.mapper

import com.example.data.database.model_db.PokemonDetailsModelDb
import com.example.domain.models.mapper.MapperInterface
import com.example.domain.models.model_vo.PokemonDetailsModelVo

class PokemonDetailsDbToDetailsVoMapper:
    MapperInterface<PokemonDetailsModelVo, PokemonDetailsModelDb> {

    override fun toOutObject(inObject: PokemonDetailsModelDb): PokemonDetailsModelVo {
        return with(inObject) {
            PokemonDetailsModelVo(
                name = pokemonName,
                avatarUrl = pokemonAvatarUrl,
                weight = weight,
                height = height,
                abilitiesNames = abilities,
                formsNames = forms,
                statsNames = stats
            )
        }
    }
}