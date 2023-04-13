package com.example.rickandmorty.ui.pages.characters

import android.util.Log
import androidx.paging.*
import androidx.paging.LoadState.Loading.endOfPaginationReached
import androidx.room.withTransaction
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.local.RickAndMortyRoomDatabase
import com.example.rickandmorty.local.cache_characters.CachedCharacterEntity
import com.example.rickandmorty.local.cache_characters.mapToCachedCharacterEntity
import com.example.rickandmorty.local.remote_keys.RemoteKeysEntity

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val api: RickAndMortyApi,
    private val database: RickAndMortyRoomDatabase
) : RemoteMediator<Int, CachedCharacterEntity>() {
    private val cacheDao = database.cacheDao
    private val remoteKeysDao = database.remoteKeysDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedCharacterEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                Log.d("MyRemoteMediator", "Loading page , loadType=${loadType.name}")
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX// always start from the first page when refreshing
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                Log.d("MyRemoteMediator", "Loading page $remoteKeys, loadType=${loadType.name}")
                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                Log.d("MyRemoteMediator", "Loading page $remoteKeys, loadType=${loadType.name}")
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val response = api.getCharacters(page)
            val characters = response.body()?.results ?: emptyList()
            val sortedCharacters = characters.sortedBy { it.id }

            database.withTransaction {
                if (loadType== LoadType.REFRESH){
                    database.remoteKeysDao.clearRemoteKeys()
                    database.cacheDao.clearCharacters()
                }
            }
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1)
            val nextKey = if ((endOfPaginationReached)) null else page.plus(1)

            Log.d("MyRemoteMediator", "Loading page $page, loadType=${loadType.name}")

            val cachedCharacters = mapToCachedCharacterEntity(characters)
            cacheDao.insertAll(cachedCharacters)

            Log.d("MyRemoteMediator", "cachedCharacters ${cacheDao.insertAll(cachedCharacters)} characters stored ")


            val remoteKeys = sortedCharacters.map {
                RemoteKeysEntity(
                    repoId = it.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            remoteKeysDao.insertAll(remoteKeys)

            return MediatorResult.Success(endOfPaginationReached = characters.isEmpty())
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CachedCharacterEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.remoteKeysDao.remoteKeysRepoId(it.id!!.toLong())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CachedCharacterEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.remoteKeysDao.remoteKeysRepoId(it.id!!.toLong())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CachedCharacterEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysDao.remoteKeysRepoId(repoId.toLong())
            }
        }
    }
}
