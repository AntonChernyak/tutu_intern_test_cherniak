package com.example.domain.mapper

import com.example.domain.models.model_dto.PokemonDto
import com.example.domain.models.model_vo.AbilityVo
import com.example.domain.models.model_vo.ItemsVo
import com.example.domain.models.model_vo.PokemonDetailsVo
import com.example.domain.models.model_vo.StatVo

class PokemonDetailsDtoToVoMapper : MapperInterface<PokemonDetailsVo, PokemonDto> {

    override fun toViewObject(dto: PokemonDto): PokemonDetailsVo {
        val abilitiesVo = dto.abilities
            .flatMap { response -> response.ability }
            .map { abilityDto -> AbilityVo(abilityName = abilityDto.name) }
        val itemsVo = dto.heldItems.flatMap { it.items }.map { ItemsVo(itemName = it.name) }
        val statsVo = dto.stats.flatMap { it.stat }.map { StatVo(statName = it.name) }

        return PokemonDetailsVo(
            pokemonName = dto.name,
            pokemonAvatarUrl = dto.sprites.frontDefaultUrl,
            weight = dto.weight,
            height = dto.height,
            abilities = abilitiesVo,
            items = itemsVo,
            stats = statsVo
        )
    }
}