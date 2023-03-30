@file:OptIn(DelicateCoroutinesApi::class)

package com.example.rickandmorty.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.response.*
import com.example.rickandmorty.ui.pages.characters.CharacterPagingSource
import com.example.rickandmorty.utils.result
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val api: RickAndMortyApi
) : IRickAndMortyRepository {

    override fun getCharacters(): Flow<PagingData<CharacterItem>> {
       return  Pager(config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { CharacterPagingSource(api) }
        ).flow
           .cachedIn(GlobalScope)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 42
    }

    override fun getEpisodes(page: Int): Flow<ApiResponse<EpisodeResponse?>> =
        result {
            api.getEpisodes(page)
        }.flowOn(Dispatchers.IO)

    override fun getLocations(page: Int): Flow<ApiResponse<LocationResponse?>> =
        result {
            api.getLocations(page)
        }.flowOn(Dispatchers.IO)


    override fun getCharacterById(id: Int): Flow<ApiResponse<CharacterItem?>> =
        result {
            api.getCharacterById(id)
        }.flowOn(Dispatchers.IO)

    override fun getCharacterEpisodes(episodeList: String): Flow<ApiResponse<List<EpisodeItem>?>> =
        result {
            api.getCharacterEpisodes(episodeList)
        }.flowOn(Dispatchers.IO)

    override fun getMoreCharactersThanOne(characterList: List<String>): Flow<ApiResponse<List<CharacterItem>?>> =
        result {
            api.getMoreCharactersThanOne(characterList)
        }.flowOn(Dispatchers.IO)


    override fun getEpisodesById(id: Int): Flow<ApiResponse<EpisodeItem?>> =
        result {
            api.getEpisodeById(id)
        }.flowOn(Dispatchers.IO)


    override fun getLocationById(id: Int): Flow<ApiResponse<LocationItem?>> =
        result {
            api.getLocationById(id)
        }.flowOn(Dispatchers.IO)

    override fun getSearch(text: String, status: String): Flow<ApiResponse<CharactersResponse?>> =
        result {
            api.getSearch(text, status)
        }.flowOn(Dispatchers.IO)


}