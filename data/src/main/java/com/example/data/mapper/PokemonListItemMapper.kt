package com.example.data.mapper

import com.example.domain.model_dto.PokemonDto
import com.example.data.database.model_vo.PokemonListVo

class PokemonListItemMapper: MapperInterface<PokemonListVo, PokemonDto> {

    override fun toViewObject(dto: PokemonDto): PokemonListVo {
        return PokemonListVo(
            pokemonName = dto.name,
            pokemonAvatarUrl = dto.sprites.frontDefaultUrl
        )
    }

}