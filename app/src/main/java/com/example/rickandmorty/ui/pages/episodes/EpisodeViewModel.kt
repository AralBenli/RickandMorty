package com.example.rickandmorty.ui.pages.episodes


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.EpisodeItem
import com.example.rickandmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {


    private val _episodes = MutableLiveData<PagingData<EpisodeItem>>()
    val episodes: LiveData<PagingData<EpisodeItem>>
        get() = _episodes

    fun fetchEpisodes() {
        viewModelScope.launch {
            repo.getEpisodes().flowOn(Dispatchers.IO).collect { pagingData ->
                _episodes.value = pagingData
            }
        }
    }
}
