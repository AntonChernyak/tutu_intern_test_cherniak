package com.example.domain.models.model_vo

class PokemonListData(
    val previousOffset: String?,
    val previousLimit: String,
    val nextOffset: String?,
    val nextLimit: String,
    val itemListVo: List<PokemonListItemModelVo>
)