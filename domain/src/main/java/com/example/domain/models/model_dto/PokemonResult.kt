package com.example.domain.models.model_dto

import kotlinx.serialization.Serializable

@Serializable
class PokemonResult(
    val name: String,
    val url: String
)