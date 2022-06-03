package com.example.domain.model_dto

import kotlinx.serialization.Serializable

@Serializable
class ItemResponse(
    val item: ItemDto
)

@Serializable
class ItemDto(
    val name: String,
    val url: String
)