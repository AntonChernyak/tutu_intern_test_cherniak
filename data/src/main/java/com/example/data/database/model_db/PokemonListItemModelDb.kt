package com.example.data.database.model_db

import androidx.room.ColumnInfo
import com.example.data.database.model_db.PokemonDetailsModelDb.Companion.POKEMON_AVATAR_URL_COLUMN_NAME
import com.example.data.database.model_db.PokemonDetailsModelDb.Companion.POKEMON_NAME_COLUMN_NAME

class PokemonListItemModelDb(
    @ColumnInfo(name = POKEMON_NAME_COLUMN_NAME)
    val pokemonName: String,
    @ColumnInfo(name = POKEMON_AVATAR_URL_COLUMN_NAME)
    val pokemonAvatarUrl: String
)