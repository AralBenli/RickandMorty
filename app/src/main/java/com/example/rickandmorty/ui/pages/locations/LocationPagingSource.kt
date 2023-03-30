package com.example.rickandmorty.ui.pages.locations

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.response.LocationItem

private const val STARTING_PAGE_INDEX = 1

class LocationPagingSource (
    private val api: RickAndMortyApi,
): PagingSource<Int, LocationItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationItem> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                api.getLocations(
                    pageNumber

                )

            var nextPageNumber: Int? = null

            if (response.body()?.info?.next != null) {
                val uri = Uri.parse(response.body()?.info?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.body()?.results!!,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocationItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}