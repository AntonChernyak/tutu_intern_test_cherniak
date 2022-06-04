package com.example.domain.models.model_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SpriteDto(
    @SerialName("front_default")
    val frontDefaultUrl: String
)