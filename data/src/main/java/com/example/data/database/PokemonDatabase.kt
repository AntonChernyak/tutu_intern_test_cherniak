package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.model_vo.PokemonDetailsVo

@Database(entities = [PokemonDetailsVo::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun getDao(): PokemonDao
}