package com.example.data.database.model_vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.model_vo.PokemonDetailsVo.Companion.POKEMONS_TABLE_NAME
import com.example.domain.models.model_vo.AbilityVo
import com.example.domain.models.model_vo.ItemsVo
import com.example.domain.models.model_vo.StatVo

@Entity(tableName = POKEMONS_TABLE_NAME)
data class PokemonDetailsVo(
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
    val abilities: List<AbilityVo>,
    @ColumnInfo(name = "items")
    val items: List<ItemsVo>,
    @ColumnInfo(name = "stats")
    val stats: List<StatVo>
) {
    companion object {
        const val POKEMONS_TABLE_NAME = "pokemons-db"
        const val POKEMON_NAME_COLUMN_NAME = "name"
        const val POKEMON_AVATAR_URL_COLUMN_NAME = "avatar_url"
    }
}

class AbilityVo(
    val abilityName: String
)

class ItemsVo(
    val itemName: String
)

class StatVo(
    val statName: String
)