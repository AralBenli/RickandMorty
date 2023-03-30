package com.example.rickandmorty.ui.pages.locations

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.response.EpisodeItem
import com.example.rickandmorty.response.LocationItem
import retrofit2.HttpException

class LocationPagingSource (
    private val api: RickAndMortyApi,
): PagingSource<Int, LocationItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationItem> {
        val pageNumber = params.key ?: 1
        return try  {
            val response = api.getLocations(pageNumber)
            val pagedResponse = response.body()
            val data = pagedResponse?.results


            var nextPageNumber : Int? = null
            if (pagedResponse?.info?.next != null){
                val uri = Uri.parse(pagedResponse.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocationItem>): Int? {
        return null
    }
}