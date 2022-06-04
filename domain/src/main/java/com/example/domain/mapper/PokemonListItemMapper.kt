package com.example.domain.mapper

import com.example.domain.models.model_vo.PokemonDetailsVo
import com.example.domain.models.model_vo.PokemonListVo

class PokemonListItemMapper: MapperInterface<PokemonListVo, PokemonDetailsVo> {

    override fun toViewObject(dto: PokemonDetailsVo): PokemonListVo {
        return PokemonListVo(
            pokemonName = dto.pokemonName,
            pokemonAvatarUrl = dto.pokemonAvatarUrl
        )
    }

}