package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.model_db.PokemonDetailsModelDb
import com.example.data.database.model_db.PokemonListItemModelDb

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonToDb(pokemonDetailsDb: PokemonDetailsModelDb)

    @Query("SELECT * FROM `pokemons-db` WHERE name =:pokemonName")
    suspend fun getPokemonDetailsFromDb(pokemonName: String): PokemonDetailsModelDb

    @Query("SELECT name, avatar_url FROM `pokemons-db`")
    suspend fun getPokemonsListWithNamesAndAvatarUrls(): List<PokemonListItemModelDb>

}