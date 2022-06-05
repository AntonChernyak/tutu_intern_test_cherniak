package com.example.data.mapper

import com.example.data.database.model_db.PokemonListItemModelDb
import com.example.domain.models.mapper.MapperInterface
import com.example.domain.models.model_vo.PokemonListItemModelVo

class PokemonListItemModelDbToListItemModelVoMapper:
    MapperInterface<PokemonListItemModelVo, PokemonListItemModelDb> {

    override fun toOutObject(inObject: PokemonListItemModelDb): PokemonListItemModelVo {
        return with(inObject) {
            PokemonListItemModelVo(
                name = pokemonName,
                avatarUrl = pokemonAvatarUrl
            )
        }
    }

}