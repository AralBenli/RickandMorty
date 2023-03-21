package com.example.rickandmorty.ui.pages.characters

import androidx.paging.PageKeyedDataSource
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.repositories.RickAndMortyRepository



class CharacterDataSource(
    private val viewModel: CharactersViewModel,
    private val repository: RickAndMortyRepository
) : PageKeyedDataSource<Int, CharactersResponse>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, CharactersResponse>
    ) {
        val characterList = repository.getCharacterById(1)
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CharactersResponse>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, CharactersResponse>
    ) {
        val characterList = repository.getCharacterById(params.key)

    }


}
