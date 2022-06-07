package com.example.domain.models.model_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StatsResponse(
    @SerialName("base_stat")
    val baseStat: Int,
    val stat: StatDto
)

@Serializable
class StatDto(
    val name: String,
)