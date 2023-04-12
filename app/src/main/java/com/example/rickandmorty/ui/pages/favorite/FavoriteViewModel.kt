package com.example.rickandmorty.ui.pages.favorite


import androidx.lifecycle.ViewModel
import com.example.rickandmorty.local.favorite.FavoriteEntity
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.FavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repo: IRickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> get() = _state


    fun fetchFavoriteCharacters(): Flow<List<FavoriteEntity>> {
        return repo.getAllFavoriteCharacters().map { list ->
            list.map { character ->
                character.apply {
                    isFavorite = true
                }
            }
        }
    }
}

