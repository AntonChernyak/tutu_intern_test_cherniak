package com.example.domain.model_dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ItemResponse(
    @SerialName("held_items")
    val items: List<ItemDto>
)

@Serializable
class ItemDto(
    val name: String,
    val url: String
)