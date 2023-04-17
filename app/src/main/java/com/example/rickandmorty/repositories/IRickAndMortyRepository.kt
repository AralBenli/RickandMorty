package com.example.rickandmorty.repositories

import androidx.paging.PagingData
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.local.cache_characters.CachedCharacterEntity
import com.example.rickandmorty.local.favorite.FavoriteEntity
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.response.CharactersResponse
import com.example.rickandmorty.response.EpisodeItem
import com.example.rickandmorty.response.LocationItem
import kotlinx.coroutines.flow.Flow

interface IRickAndMortyRepository {

    fun getCharacters(): Flow<PagingData<CachedCharacterEntity>>

    fun getEpisodes(): Flow<PagingData<EpisodeItem>>

    fun getLocations(): Flow<PagingData<LocationItem>>

    fun getCharacterById(id: Int): Flow<ApiResponse<CharacterItem?>>

    fun getCharacterEpisodes(
        episodeList: String
    ): Flow<ApiResponse<List<EpisodeItem>?>>

    fun getMoreCharactersThanOne(
        characterList: List<String>
    ): Flow<ApiResponse<List<CharacterItem>?>>

    fun getEpisodesById(id: Int): Flow<ApiResponse<EpisodeItem?>>


    fun getLocationById(id: Int): Flow<ApiResponse<LocationItem?>>

    fun getSearch(
        text: String,
        status: String
    ): Flow<ApiResponse<CharactersResponse?>>

    fun getSearchAll(
        text: String
    ): Flow<ApiResponse<CharactersResponse?>>



    fun getAllFavoriteCharacters(): Flow<List<FavoriteEntity>>

    suspend fun addCharacterToFavoriteList(character: FavoriteEntity)

    suspend fun deleteCharacterFromMyFavoriteList(character: FavoriteEntity)

    suspend fun updateFavoriteState(id: Int, isFavorite: Boolean)

    suspend fun updateCharacter(character: FavoriteEntity?)

    suspend fun isCharacterInFavorites(id: Int): Boolean

    fun getFavoriteCharacters(): Flow<List<FavoriteEntity>>

}

