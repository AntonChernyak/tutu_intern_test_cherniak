package com.example.domain.models.model_vo

data class PokemonDetailsModelVo(
    val name: String,
    val avatarUrl: String,
    val weight: Int,
    val height: Int,
    val abilitiesNames: List<String>,
    val formsNames: List<String>,
    val statsNames: List<String>
)