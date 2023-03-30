package com.example.rickandmorty.ui.pages.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.response.CharactersResponse
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.ui.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {

    private val _characters = MutableLiveData<PagingData<CharacterItem>>()
    val characters: LiveData<PagingData<CharacterItem>>
        get() = _characters

    fun fetchCharacters() {
        viewModelScope.launch {
            repo.getCharacters().flowOn(Dispatchers.IO).collect { pagingData ->
                _characters.value = pagingData
            }
        }
    }
}




