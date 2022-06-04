package com.example.domain.mapper

import com.example.domain.model_dto.PokemonDto
import com.example.domain.model_vo.AbilityVo
import com.example.domain.model_vo.ItemsVo
import com.example.domain.model_vo.PokemonDetailsVo
import com.example.domain.model_vo.StatVo

class PokemonDetailsDtoToVoMapper : MapperInterface<PokemonDetailsVo, PokemonDto> {

    override fun toViewObject(dto: PokemonDto): PokemonDetailsVo {
        val abilitiesVo = dto.abilities.flatMap { it.ability }.map { AbilityVo(it.name) }
        val itemsVo = dto.heldItems.flatMap { it.items }.map { ItemsVo(it.name) }
        val statsVo = dto.stats.flatMap { it.stat }.map { StatVo(it.name) }

        return PokemonDetailsVo(
            name = dto.name,
            avatarUrl = dto.sprites.frontDefaultUrl,
            weight = dto.weight,
            height = dto.height,
            abilities = abilitiesVo,
            items = itemsVo,
            stats = statsVo
        )
    }
}