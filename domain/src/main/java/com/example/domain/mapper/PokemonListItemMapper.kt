package com.example.domain.mapper

import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.PokemonListVo

class PokemonListItemMapper: MapperInterface<PokemonListVo, PokemonDto> {

    override fun toViewObject(dto: PokemonDto): PokemonListVo {
        return PokemonListVo(
            pokemonName = dto.name,
            pokemonAvatarUrl = dto.sprites.frontDefaultUrl
        )
    }

}