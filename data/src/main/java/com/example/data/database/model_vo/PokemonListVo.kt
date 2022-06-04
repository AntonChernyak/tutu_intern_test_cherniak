package com.example.data.database.model_vo

import androidx.room.ColumnInfo
import com.example.data.database.model_vo.PokemonDetailsVo.Companion.POKEMON_AVATAR_URL_COLUMN_NAME
import com.example.data.database.model_vo.PokemonDetailsVo.Companion.POKEMON_NAME_COLUMN_NAME

data class PokemonListVo(
    @ColumnInfo(name = POKEMON_NAME_COLUMN_NAME)
    val pokemonName: String,
    @ColumnInfo(name = POKEMON_AVATAR_URL_COLUMN_NAME)
    val pokemonAvatarUrl: String
)