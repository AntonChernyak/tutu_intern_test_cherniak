package com.example.domain.models.model_dto

import kotlinx.serialization.Serializable

@Serializable
class PokemonDto(
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<AbilityResponse>,
    val forms: List<FormDto>,
    val stats: List<StatsResponse>,
    val sprites: SpriteDto
)