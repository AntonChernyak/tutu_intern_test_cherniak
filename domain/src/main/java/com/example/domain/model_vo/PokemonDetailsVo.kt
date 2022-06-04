package com.example.domain.model_vo

data class PokemonDetailsVo(
    val name: String,
    val avatarUrl: String,
    val weight: Int,
    val height: Int,
    val abilities: List<AbilityVo>,
    val items: List<ItemsVo>,
    val stats: List<StatVo>
)

class AbilityVo(
    val abilityName: String
)

class ItemsVo(
    val itemName: String
)

class StatVo(
    val statName: String
)