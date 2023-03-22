package com.example.rickandmorty.repositories

import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.models.EpisodeResponse
import com.example.rickandmorty.models.LocationResponse
import com.example.rickandmorty.utils.result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Query

class RickAndMortyRepository(
    private val api: RickAndMortyApi
) : IRickAndMortyRepository {

    override fun getCharacters(page: Int): Flow<ApiResponse<CharactersResponse?>> =
        result {
            api.getCharacters(page)
        }.flowOn(Dispatchers.IO)

    override fun getCharacterById(id: Int): Flow<ApiResponse<CharacterItem?>> =
        result {
            api.getCharacterById(id)
        }.flowOn(Dispatchers.IO)

    override fun getEpisodes(page: Int): Flow<ApiResponse<EpisodeResponse?>> =
        result {
            api.getEpisodes(page)
        }.flowOn(Dispatchers.IO)

    override fun getLocations(page: Int): Flow<ApiResponse<LocationResponse?>> =
        result {
            api.getLocations(page)
        }.flowOn(Dispatchers.IO)

    override fun getSearch(text: String, status: String): Flow<ApiResponse<CharactersResponse?>> =
        result {
            api.getSearch(text, status)
        }.flowOn(Dispatchers.IO)


}