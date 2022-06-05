package com.example.data.database.typeconverters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class StringsListTypeConverter {

    @Inject
    lateinit var json: Json

    @TypeConverter
    fun fromList(abilitiesList: List<String>): String = json.encodeToString(abilitiesList)

    @TypeConverter
    fun toList(jsonString: String): List<String> = json.decodeFromString(jsonString)
}