package com.example.domain.models.model_dto

import kotlinx.serialization.Serializable

@Serializable
class PokemonsResponse(
    val results: List<PokemonResult>
)