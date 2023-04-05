package com.example.rickandmorty.repositories

import androidx.paging.PagingData
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.response.*
import kotlinx.coroutines.flow.Flow

interface IRickAndMortyRepository {

    fun getCharacters(): Flow<PagingData<CharacterItem>>

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

    fun getAllFavoriteCharacters(): Flow<List<CharacterItem>>

    suspend fun addCharacterToFavoriteList(character: CharacterItem)

    suspend fun deleteCharacterFromMyFavoriteList(character: CharacterItem)

    suspend fun updateCharacters(character : CharacterItem)

}