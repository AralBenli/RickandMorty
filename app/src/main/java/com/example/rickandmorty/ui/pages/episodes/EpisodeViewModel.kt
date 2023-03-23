package com.example.rickandmorty.ui.pages.episodes


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.response.EpisodeResponse
import com.example.rickandmorty.ui.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {


    private val episodeStateFlow: MutableSharedFlow<EpisodeResponse?> =
        MutableSharedFlow()
    val _episodeStateFlow: SharedFlow<EpisodeResponse?> = episodeStateFlow

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    private val toastMessageObserver: MutableLiveData<String> = MutableLiveData()


    fun fetchEpisodes(page : Int) {
        viewModelScope.launch {
            repo.getEpisodes(page).collectLatest {
                when (it) {
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }
                    is ApiResponse.Success -> {
                        episodeStateFlow.emit(it.data)
                        progressStateFlow.value = false

                    }
                    is ApiResponse.Failure -> {
                        try {
                            val errorResponse = Gson().fromJson(
                                it.data!!.errorBody()!!.string(),
                                EpisodeResponse::class.java
                            )
                            episodeStateFlow.emit(errorResponse)
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

