package com.example.rickandmorty.ui.pages.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.EpisodeItem
import com.example.rickandmorty.response.LocationItem
import com.example.rickandmorty.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {

    private val locationStateFlow: MutableSharedFlow<PagingData<LocationItem>> = MutableSharedFlow()
    val _locationStateFlow: SharedFlow<PagingData<LocationItem>> = locationStateFlow

    fun fetchLocations() {
        viewModelScope.launch {
            repo.getLocations().flowOn(Dispatchers.IO).collectLatest {
                locationStateFlow.emit(it)
            }
        }
    }
}

