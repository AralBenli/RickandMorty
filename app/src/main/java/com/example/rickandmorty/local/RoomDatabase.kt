package com.example.rickandmorty.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.utils.ConverterForRoom

@Database(
    entities = [CharacterItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConverterForRoom::class)
abstract class RickAndMortyRoomDatabase : RoomDatabase() {
    abstract val rickMortyDao: RickAndMortyDao
}