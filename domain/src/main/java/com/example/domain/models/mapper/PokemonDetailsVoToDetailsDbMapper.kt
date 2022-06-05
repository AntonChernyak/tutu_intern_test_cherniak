package com.example.domain.models.mapper

import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.PokemonListItemModelVo


class PokemonDetailsDtoToListItemVoMapper :
    MapperInterface<PokemonListItemModelVo, PokemonDto> {
    override fun toOutObject(inObject: PokemonDto): PokemonListItemModelVo {
        return with(inObject) {
            PokemonListItemModelVo(
                name = name,
                avatarUrl = sprites.frontDefaultUrl
            )
        }
    }
}