package com.example.rickandmorty.ui.pages.locations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.response.LocationResponse
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.response.EpisodeItem
import com.example.rickandmorty.ui.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {


    private val locationStateFlow: MutableSharedFlow<LocationResponse?> =
        MutableSharedFlow()
    val _locationStateFlow: SharedFlow<LocationResponse?> = locationStateFlow

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    private val toastMessageObserver: MutableLiveData<String> = MutableLiveData()


    fun fetchLocations(page : Int) {
        viewModelScope.launch {
            repo.getLocations(page).collectLatest {
                when (it) {
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }
                    is ApiResponse.Success -> {
                        locationStateFlow.emit(it.data)
                        progressStateFlow.value = false

                    }
                    is ApiResponse.Failure -> {
                        try {
                            val errorResponse = Gson().fromJson(
                                it.data!!.errorBody()!!.string(),
                                LocationResponse::class.java
                            )
                            locationStateFlow.emit(errorResponse)
                        } catch (e: Exception) {
                            toastMessageObserver.setValue("Connection failed.")
                        }
                        progressStateFlow.value = false

                    }
                }
            }
        }
    }


}

