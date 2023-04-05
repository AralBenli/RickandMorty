package com.example.rickandmorty.utils

import androidx.room.TypeConverter
import com.example.rickandmorty.response.CharLocation
import com.example.rickandmorty.response.CharOrigin
import com.google.gson.Gson

class ConverterForRoom {
    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun charToJson(value: CharLocation?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToChar(value: String) =
        Gson().fromJson(value, CharLocation::class.java)

    @TypeConverter
    fun originToJson(value: CharOrigin?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToOrigin(value: String) =
        Gson().fromJson(value, CharOrigin::class.java)

}