package com.example.domain.models.model_vo

abstract class ItemDetails{

    abstract val uniqueName: String
}

data class TopLevelItem(
    val detailName: String,
    var isExpandable: Boolean = false
) : ItemDetails() {
    override val uniqueName: String = detailName
}

class LowLevelItem(
    val detailName: String
): ItemDetails() {
    override val uniqueName: String = detailName
}