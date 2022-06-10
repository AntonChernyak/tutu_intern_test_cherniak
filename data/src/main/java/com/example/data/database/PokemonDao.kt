package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.model_db.PokemonDetailsModelDb
import com.example.data.database.model_db.PokemonDetailsModelDb.Companion.POKEMONS_TABLE_NAME
import com.example.data.database.model_db.PokemonListItemModelDb

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonToDb(pokemonDetailsDb: PokemonDetailsModelDb)

    @Query("SELECT * FROM $POKEMONS_TABLE_NAME WHERE name =:pokemonName")
    suspend fun getPokemonDetailsFromDb(pokemonName: String): PokemonDetailsModelDb

    @Query("SELECT name, avatar_url FROM $POKEMONS_TABLE_NAME ORDER BY id LIMIT :limit OFFSET :offset")
    suspend fun getPokemonsListWithNamesAndAvatarUrls(
        offset: String, limit: String
    ): List<PokemonListItemModelDb>

    @Query("DELETE FROM $POKEMONS_TABLE_NAME")
    suspend fun clearDatabase()
}