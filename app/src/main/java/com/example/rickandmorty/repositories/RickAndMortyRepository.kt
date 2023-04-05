package com.example.rickandmorty.repositories

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.local.RickAndMortyDao
import com.example.rickandmorty.response.*
import com.example.rickandmorty.ui.pages.characters.CharacterPagingSource
import com.example.rickandmorty.ui.pages.episodes.EpisodePagingSource
import com.example.rickandmorty.ui.pages.locations.LocationPagingSource
import com.example.rickandmorty.utils.result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val api: RickAndMortyApi,
    private val dao: RickAndMortyDao,
    private val scope : CoroutineScope
) : IRickAndMortyRepository {

    companion object {
        private const val NETWORK_PAGE_SIZE_CHARACTER = 42
        private const val NETWORK_PAGE_SIZE_EPISODE = 3
        private const val NETWORK_PAGE_SIZE_LOCATION = 7
    }

    override fun getCharacters(): Flow<PagingData<CharacterItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE_CHARACTER, enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(api) }
        ).flow
            .cachedIn(scope)
    }

    override fun getEpisodes(): Flow<PagingData<EpisodeItem>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE_EPISODE, enablePlaceholders = false
        ),
            pagingSourceFactory = { EpisodePagingSource(api) }
        ).flow
            .cachedIn(scope)
    }


    override fun getLocations(): Flow<PagingData<LocationItem>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE_LOCATION, enablePlaceholders = false
        ),
            pagingSourceFactory = { LocationPagingSource(api) }
        ).flow
            .cachedIn(scope)
    }


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

    override fun getAllFavoriteCharacters(): Flow<List<CharacterItem>> {
        return dao.getAllFavoriteCharacters()
    }

    override suspend fun addCharacterToFavoriteList(character: CharacterItem) {
        character.isFavorite = true
        dao.addFavoriteCharacter(character)
        dao.updateCharacter(character)
        dao.updateFavoriteState(character.id!!, true)

    }

    override suspend fun deleteCharacterFromMyFavoriteList(character: CharacterItem) {
        character.isFavorite = false
        dao.deleteFavoriteCharacter(character)
        dao.updateCharacter(character)
        dao.updateFavoriteState(character.id!!, false)

    }

    override suspend fun updateFavoriteState(id: Int, isFavorite: Boolean) {
        dao.updateFavoriteState(id, isFavorite)
    }

    override suspend fun updateCharacter(character: CharacterItem?) {
        dao.updateCharacter(character)
    }

    override suspend fun isCharacterInFavorites(id: Int): Boolean =
        withContext(Dispatchers.IO) {
        dao.isCharacterInFavorites(id)
    }


}