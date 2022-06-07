package com.example.data.database.model_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.model_db.PokemonDetailsModelDb.Companion.POKEMONS_TABLE_NAME

@Entity(tableName = POKEMONS_TABLE_NAME)
class PokemonDetailsModelDb(
    @PrimaryKey
    @ColumnInfo(name = POKEMON_NAME_COLUMN_NAME)
    val pokemonName: String,
    @ColumnInfo(name = POKEMON_AVATAR_URL_COLUMN_NAME)
    val pokemonAvatarUrl: String,
    @ColumnInfo(name = "weight")
    val weight: Int,
    @ColumnInfo(name = "height")
    val height: Int,
    @ColumnInfo(name = "abilities")
    val abilities: List<String>,
    @ColumnInfo(name = "forms")
    val forms: List<String>,
    @ColumnInfo(name = "stats")
    val stats: List<String>
) {
    companion object {
        const val POKEMONS_TABLE_NAME = "pokemons_table"
        const val POKEMON_NAME_COLUMN_NAME = "name"
        const val POKEMON_AVATAR_URL_COLUMN_NAME = "avatar_url"
    }
}