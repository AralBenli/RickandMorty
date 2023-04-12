package com.example.rickandmorty.ui.pages.characters

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.local.cache_characters.CachedCharacterEntity
import com.example.rickandmorty.response.CharacterItem
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class CharacterPagingSource @Inject constructor(
    private val api: RickAndMortyApi
) : PagingSource<Int, CachedCharacterEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CachedCharacterEntity> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        Log.d("MyPagingSource", "Loading page $pageNumber")

        return try {
            val response = api.getCharacters(pageNumber)

            var nextPageNumber: Int? = null

            if (response.body()?.info?.next != null) {
                val uri = Uri.parse(response.body()?.info?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            val results = response.body()?.results?.map { it.toCachedCharacterItem() } ?: emptyList()

            LoadResult.Page(
                data = results,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CachedCharacterEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

fun CharacterItem.toCachedCharacterItem() = CachedCharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin,
    location = location,
    image = image
)
