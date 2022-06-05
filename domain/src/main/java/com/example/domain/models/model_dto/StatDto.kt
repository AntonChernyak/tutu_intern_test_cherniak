package com.example.domain.models.model_dto

import kotlinx.serialization.Serializable

@Serializable
class StatsResponse(
    val stat: StatDto
)

@Serializable
class StatDto(
    val name: String,
)