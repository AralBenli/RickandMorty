package com.example.rickandmorty.repositories

import androidx.paging.*
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.local.RickAndMortyRoomDatabase
import com.example.rickandmorty.local.cache_characters.CachedCharacterEntity
import com.example.rickandmorty.local.favorite.FavoriteEntity
import com.example.rickandmorty.response.*
import com.example.rickandmorty.ui.pages.characters.CharacterRemoteMediator
import com.example.rickandmorty.ui.pages.episodes.EpisodePagingSource
import com.example.rickandmorty.ui.pages.locations.LocationPagingSource
import com.example.rickandmorty.utils.result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val api: RickAndMortyApi,
    private val db: RickAndMortyRoomDatabase,
    private val scope: CoroutineScope
) : IRickAndMortyRepository {

    private val cacheDao = db.cacheDao
    private val favoriteDao = db.favoriteDao

    companion object {
        private const val NETWORK_PAGE_SIZE_CHARACTER = 42
        private const val NETWORK_PAGE_SIZE_EPISODE = 3
        private const val NETWORK_PAGE_SIZE_LOCATION = 7
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(): Flow<PagingData<CachedCharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE_CHARACTER,
                enablePlaceholders = false
            ),
            initialKey = 1,
            remoteMediator = CharacterRemoteMediator(api, db),
            pagingSourceFactory = {
                cacheDao.getCharacters()
            }
        ).flow
            .cachedIn(scope)
    }

    override fun getFavoriteCharacters(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavoriteCharacters()
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

    override fun getAllFavoriteCharacters(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavoriteCharacters()
    }


    override suspend fun addCharacterToFavoriteList(character: FavoriteEntity) {
        character.isFavorite = true
        favoriteDao.addFavoriteCharacter(character)
        favoriteDao.updateCharacter(character)
        favoriteDao.updateFavoriteState(character.id!!, true)
    }

    override suspend fun deleteCharacterFromMyFavoriteList(character: FavoriteEntity) {
        character.isFavorite = false
        favoriteDao.deleteFavoriteCharacter(character)
        favoriteDao.updateCharacter(character)
        favoriteDao.updateFavoriteState(character.id!!, false)
    }

    override suspend fun updateFavoriteState(id: Int, isFavorite: Boolean) {
        favoriteDao.updateFavoriteState(id, isFavorite)
    }

    override suspend fun updateCharacter(character: FavoriteEntity?) {
        favoriteDao.updateCharacter(character)
    }

    override suspend fun isCharacterInFavorites(id: Int): Boolean =
        withContext(Dispatchers.IO) {
            favoriteDao.isCharacterInFavorites(id)
        }


}