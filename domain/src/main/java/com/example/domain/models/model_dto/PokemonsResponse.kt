package com.example.domain.models.model_dto

import kotlinx.serialization.Serializable

@Serializable
class PokemonsResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<PokemonResult>
)