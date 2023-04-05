package com.example.rickandmorty.local

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.IGNORE
import com.example.rickandmorty.response.CharacterItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RickAndMortyDao {


    @Query("SELECT * FROM characterItem")
    fun getAllFavoriteCharacters(): Flow<List<CharacterItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCharacter(character: CharacterItem?)

    @Delete
    suspend fun deleteFavoriteCharacter(character: CharacterItem?)

    @Update
    suspend fun updateCharacter(character: CharacterItem?)
}