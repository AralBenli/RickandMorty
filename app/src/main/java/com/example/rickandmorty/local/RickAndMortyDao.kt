package com.example.rickandmorty.local

import androidx.room.*
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

    @Query("UPDATE characterItem SET isFavorite=:isFavorite WHERE id=:id")
    suspend fun updateFavoriteState(id: Int, isFavorite: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM characterItem WHERE id = :id)")
    suspend fun isCharacterInFavorites(id: Int): Boolean

}