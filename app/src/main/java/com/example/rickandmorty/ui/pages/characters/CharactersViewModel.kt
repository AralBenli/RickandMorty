package com.example.rickandmorty.ui.pages.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {


    private val characterStateFlow: MutableSharedFlow<PagingData<CharacterItem>> = MutableSharedFlow()
    val _characterStateFlow: SharedFlow<PagingData<CharacterItem>> = characterStateFlow

    fun fetchCharacters() {
        viewModelScope.launch {
            repo.getCharacters().flowOn(Dispatchers.IO).collect {
                characterStateFlow.emit(it)
            }
        }
    }
}




