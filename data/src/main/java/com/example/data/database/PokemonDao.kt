package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.model_vo.PokemonDetailsVo.Companion.POKEMONS_TABLE_NAME
import com.example.domain.models.model_vo.PokemonDetailsVo
import com.example.domain.models.model_vo.PokemonListVo

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonToDb(pokemonDetailsVo: PokemonDetailsVo)

    @Query("SELECT * FROM $POKEMONS_TABLE_NAME WHERE name =:pokemonName")
    suspend fun getPokemonDetailsFromDb(pokemonName: String): PokemonDetailsVo

    @Query("SELECT name, avatar_url FROM $POKEMONS_TABLE_NAME")
    suspend fun getPokemonsListWithNamesAndAvatarUrls(): List<PokemonListVo>

}