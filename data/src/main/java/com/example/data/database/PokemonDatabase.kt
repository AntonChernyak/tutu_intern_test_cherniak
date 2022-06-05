package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.model_db.PokemonDetailsModelDb
import com.example.data.database.typeconverters.StringsListTypeConverter

@Database(entities = [PokemonDetailsModelDb::class], version = 1)
@TypeConverters(StringsListTypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun getDao(): PokemonDao
}