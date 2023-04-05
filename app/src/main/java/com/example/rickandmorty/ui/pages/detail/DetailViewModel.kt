package com.example.rickandmorty.ui.pages.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.EpisodeItem
import com.example.rickandmorty.response.FavoriteState
import com.example.rickandmorty.ui.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {

    private val detailStateFlow: MutableSharedFlow<CharacterItem?> =
        MutableSharedFlow()
    val _detailStateFlow: SharedFlow<CharacterItem?> = detailStateFlow

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    private val toastMessageObserver: MutableLiveData<String> = MutableLiveData()




    fun fetchCharacterById(id: Int) {
        viewModelScope.launch {
            repo.getCharacterById(id).collectLatest {
                when (it) {
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }
                    is ApiResponse.Success -> {
                        val character = it.data
                        val isFavorite = character?.let { id -> repo.isCharacterInFavorites(id.id!!) }
                        character?.isFavorite = isFavorite!!
                        detailStateFlow.emit(character)
                        progressStateFlow.value = false
                    }
                    is ApiResponse.Failure -> {
                        try {
                            val errorResponse = Gson().fromJson(
                                it.data!!.errorBody()!!.string(),
                                CharacterItem::class.java
                            )
                            detailStateFlow.emit(errorResponse)
                        } catch (e: Exception) {
                            toastMessageObserver.setValue("Connection failed.")
                        }
                        progressStateFlow.value = false
                    }
                }
            }
        }
    }



    private val characterEpisodesStateFlow: MutableSharedFlow<List<EpisodeItem>?> =
        MutableSharedFlow()
    val _characterEpisodesStateFlow : SharedFlow<List<EpisodeItem>?> =
        characterEpisodesStateFlow


    fun fetchEpisodesFromCharacter(episodeList: String) {
        viewModelScope.launch {
            repo.getCharacterEpisodes(episodeList).collectLatest {
                when (it) {
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }
                    is ApiResponse.Success -> {
                        characterEpisodesStateFlow.emit(it.data)
                        progressStateFlow.value = false

                    }
                    is ApiResponse.Failure -> {

                        toastMessageObserver.value = "Connection failed."

                        progressStateFlow.value = false

                    }
                }
            }
        }
    }

    var isFavorite = false
    fun addCharacterToFavorites(character: CharacterItem) {
        character.isFavorite = true
        viewModelScope.launch {
            viewModelScope.launch {
                repo.addCharacterToFavoriteList(character)
            }
        }
    }

    fun deleteCharacter(character: CharacterItem) {
        character.isFavorite = false
        viewModelScope.launch {
            repo.deleteCharacterFromMyFavoriteList(character)

        }
    }

    fun updateFavoriteState(id: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repo.updateFavoriteState(id, isFavorite)
        }
    }



}