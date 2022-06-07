package com.example.domain.models.model_vo

data class PokemonDetailsModelVo(
    val name: String,
    val avatarUrl: String,
    val weight: Int,
    val height: Int,
    val detailsRecyclerViewMap: HashMap<TopLevelItem, List<LowLevelItem>>
)