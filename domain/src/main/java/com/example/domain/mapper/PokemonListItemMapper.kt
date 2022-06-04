package com.example.domain.mapper

import com.example.domain.model_dto.PokemonDto
import com.example.domain.model_vo.PokemonListVo

class PokemonListItemMapper: MapperInterface<PokemonListVo, PokemonDto> {

    override fun toViewObject(dto: PokemonDto): PokemonListVo {
        return PokemonListVo(
            name = dto.name,
            avatarUrl = dto.sprites.frontDefaultUrl
        )
    }

}