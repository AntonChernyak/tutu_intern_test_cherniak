package com.example.domain.model_dto

import kotlinx.serialization.Serializable

@Serializable
class AbilityResponse(
    val isHidden: Boolean,
    val slot: Int,
    val ability: List<AbilityDto>
)

@Serializable
class AbilityDto(
    val name: String,
    val url: String
)