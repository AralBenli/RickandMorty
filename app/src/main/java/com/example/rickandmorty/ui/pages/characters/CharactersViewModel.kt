package com.example.rickandmorty.ui.pages.characters

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickandmorty.local.cache_characters.CachedCharacterEntity
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {

    private val characterStateFlow: MutableSharedFlow<PagingData<CachedCharacterEntity>> = MutableSharedFlow()
    val _characterStateFlow: SharedFlow<PagingData<CachedCharacterEntity>> = characterStateFlow

    init {
        Log.d("CharactersViewModel", "ViewModel initialized")
    }
    fun fetchCharacters() {
        viewModelScope.launch {
            repo.getCharacters().flowOn(Dispatchers.IO).collectLatest {
                characterStateFlow.emit(it)
            }
        }
    }
}






