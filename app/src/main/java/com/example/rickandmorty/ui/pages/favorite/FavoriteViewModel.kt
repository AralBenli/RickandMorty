package com.example.rickandmorty.ui.pages.favorite


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.response.FavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repo: IRickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> get() = _state
    var isFavorite = false



    init {
        viewModelScope.launch {
            try {
                fetchFavoriteCharacters().collect {
                    _state.value = _state.value.copy(
                        characterList = it,
                        isError = true
                    )
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    characterList = emptyList(),
                    isError = true
                )
            }
        }
    }



    fun fetchFavoriteCharacters(): Flow<List<CharacterItem>> {
        return repo.getAllFavoriteCharacters()

    }


    fun addCharacterToFavorites(character: CharacterItem) {
        character.isFavorite = true
        viewModelScope.launch {
            repo.addCharacterToFavoriteList(character)
        }
    }
    fun updateCharacter(character: CharacterItem) {
        viewModelScope.launch {
            repo.updateCharacters(character)
        }
    }

    fun deleteCharacter(charactersDomain: CharacterItem) {
        charactersDomain.isFavorite = false
        viewModelScope.launch {
            repo.deleteCharacterFromMyFavoriteList(charactersDomain)
        }
    }
}

