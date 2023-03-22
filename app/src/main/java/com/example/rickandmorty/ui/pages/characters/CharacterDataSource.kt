package com.example.rickandmorty.ui.pages.characters

import androidx.paging.PageKeyedDataSource
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.repositories.RickAndMortyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class CharacterDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: RickAndMortyRepository
) : PageKeyedDataSource<Int, CharactersResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharactersResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCharacterById(1)

            if (page == null) {
                callback.onResult(emptyList(), null, null)
                return@launch
            }
/*
            callback.onResult(page, getPageIndexFromNext(page.info.next))
*/
        }

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CharactersResponse>
    ) {
        coroutineScope.launch {
            val page = repository.getCharacterById(params.key)

            if (page == null) {
                callback.onResult(emptyList(), null)
                return@launch
            }

/*
            callback.onResult(page.results, getPageIndexFromNext(page.info.next))
*/
        }
    }
    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CharactersResponse>
    ) {
        TODO("Not yet implemented")
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}
