package com.example.data.database.typeconverters

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StringsListTypeConverter {

    @TypeConverter
    fun fromList(abilitiesList: List<String>): String = Json.encodeToString(abilitiesList)

    @TypeConverter
    fun toList(jsonString: String): List<String> = Json.decodeFromString(jsonString)
}