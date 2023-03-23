package com.example.rickandmorty.repositories

import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.response.*
import kotlinx.coroutines.flow.Flow

interface IRickAndMortyRepository {

    fun getCharacters(page: Int): Flow<ApiResponse<CharactersResponse?>>

    fun getCharacterById(id: Int): Flow<ApiResponse<CharacterItem?>>

    fun getCharacterEpisodes(
        episodeList: String
    ): Flow<ApiResponse<List<EpisodeItem>?>>

    fun getMoreCharactersThanOne(
        characterList: List<String>
    ): Flow<ApiResponse<List<CharacterItem>?>>

    fun getEpisodes(page: Int): Flow<ApiResponse<EpisodeResponse?>>


    fun getEpisodesById(id: Int): Flow<ApiResponse<EpisodeItem?>>


    fun getLocations(page: Int): Flow<ApiResponse<LocationResponse?>>


    fun getLocationById(id : Int): Flow<ApiResponse<LocationItem?>>

    fun getSearch(
        text: String,
        status: String
    ): Flow<ApiResponse<CharactersResponse?>>


}