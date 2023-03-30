package com.example.rickandmorty.ui.pages.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.rickandmorty.repositories.IRickAndMortyRepository
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

    private val _locations = MutableLiveData<PagingData<LocationItem>>()
    val locations: LiveData<PagingData<LocationItem>>
        get() = _locations

    fun fetchLocations() {
        viewModelScope.launch {
            repo.getLocations().flowOn(Dispatchers.IO).collect { pagingData ->
                _locations.value = pagingData
            }
        }
    }
}

