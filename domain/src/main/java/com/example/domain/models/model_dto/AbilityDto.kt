package com.example.domain.models.model_dto

import kotlinx.serialization.Serializable

@Serializable
class AbilityResponse(
    val ability: AbilityDto
)

@Serializable
class AbilityDto(
    val name: String,
)