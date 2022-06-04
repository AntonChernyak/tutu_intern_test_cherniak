package com.example.domain.models.model_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PokemonDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val abilities: List<AbilityResponse>,
    @SerialName("held_items")
    val heldItems: List<ItemResponse>,
    val stats: List<StatsResponse>,
    val sprites: SpriteDto
)