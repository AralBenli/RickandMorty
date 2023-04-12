package com.example.rickandmorty.local.cache_characters

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedCharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CachedCharacterEntity>)

    @Query("SELECT * FROM cached_characters")
    fun getCharacters(): PagingSource<Int, CachedCharacterEntity>

    @Query("DELETE FROM cached_characters")
    suspend fun clearCharacters()

    @Query("SELECT * FROM cached_characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CachedCharacterEntity?
}
