package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.model_vo.PokemonDetailsVo
import com.example.data.database.model_vo.PokemonDetailsVo.Companion.POKEMONS_TABLE_NAME
import com.example.data.database.model_vo.PokemonListVo
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonToDb(pokemonListVo: PokemonDetailsVo)

    @Query("SELECT * FROM $POKEMONS_TABLE_NAME")
    suspend fun getAllPokemonsFromDbToListFragment(): Flow<List<PokemonDetailsVo>>

    // mb:
    @Query("SELECT name, avatar_url FROM $POKEMONS_TABLE_NAME")
    suspend fun getPokemonNameAndAvatarUrl(): List<PokemonListVo>

}