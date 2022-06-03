package com.example.domain.model_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StatsResponse(
    val effort: Int,
    @SerialName("base_state")
    val baseState: Int,
    val stat: List<StatDto>
)

@Serializable
class StatDto(
    val name: String,
    val url: String
)