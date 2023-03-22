package com.example.rickandmorty.repositories

import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.models.EpisodeResponse
import com.example.rickandmorty.models.LocationResponse
import kotlinx.coroutines.flow.Flow

interface IRickAndMortyRepository {

    fun getCharacters(page: Int): Flow<ApiResponse<CharactersResponse?>>

    fun getCharacterById(id: Int): Flow<ApiResponse<CharacterItem?>>

    fun getEpisodes(page: Int): Flow<ApiResponse<EpisodeResponse?>>

    fun getLocations(page: Int): Flow<ApiResponse<LocationResponse?>>

    fun getSearch(
        text: String,
        status: String
    ): Flow<ApiResponse<CharactersResponse?>>


}