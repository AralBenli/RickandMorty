package com.example.rickandmorty.ui.pages.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.repositories.RickAndMortyRepository
import com.example.rickandmorty.utils.result
import retrofit2.HttpException

class CharacterPagingSource (
    private val repository : RickAndMortyRepository,
    private val api : RickAndMortyApi
    ):PagingSource<Int , CharacterItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> {
        return try  {
            val currentPage = params.key ?: 1
            val response = repository.getCharacters(currentPage)
            val data = result { api.getCharacters(currentPage) }
            val responseData = mutableListOf<CharacterItem>()


            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterItem>): Int? {
        return null
    }
}


