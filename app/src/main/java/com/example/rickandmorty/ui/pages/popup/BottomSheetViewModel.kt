package com.example.rickandmorty.ui.pages.popup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.EpisodeItem
import com.example.rickandmorty.response.LocationItem
import com.example.rickandmorty.ui.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {

    private val episodeByIdStateFlow: MutableSharedFlow<EpisodeItem?> =
        MutableSharedFlow()
    val _episodeByIdStateFlow: SharedFlow<EpisodeItem?> = episodeByIdStateFlow

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    private val toastMessageObserver: MutableLiveData<String> = MutableLiveData()


    fun fetchEpisodeById(id: Int) {
        viewModelScope.launch {
            repo.getEpisodesById(id).collectLatest {
                when (it) {
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }
                    is ApiResponse.Success -> {
                        episodeByIdStateFlow.emit(it.data)
                        progressStateFlow.value = false

                    }
                    is ApiResponse.Failure -> {
                        try {
                            val errorResponse = Gson().fromJson(
                                it.data!!.errorBody()!!.string(),
                                EpisodeItem::class.java
                            )
                            episodeByIdStateFlow.emit(errorResponse)
                        } catch (e: Exception) {
                            toastMessageObserver.setValue("Connection failed.")
                        }
                        progressStateFlow.value = false

                    }
                }
            }
        }
    }


    private val locationByIdStateFlow: MutableSharedFlow<LocationItem?> =
        MutableSharedFlow()
    val _locationByIdStateFlow: SharedFlow<LocationItem?> = locationByIdStateFlow


    fun fetchLocationById(id: Int) {
        viewModelScope.launch {
            repo.getLocationById(id).collectLatest {

                when (it) {
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }
                    is ApiResponse.Success -> {
                        locationByIdStateFlow.emit(it.data)
                        progressStateFlow.value = false

                    }
                    is ApiResponse.Failure -> {
                        try {
                            val errorResponse = Gson().fromJson(
                                it.data!!.errorBody()!!.string(),
                                LocationItem::class.java
                            )
                            locationByIdStateFlow.emit(errorResponse)
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