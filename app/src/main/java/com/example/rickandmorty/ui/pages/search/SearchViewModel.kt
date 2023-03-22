package com.example.rickandmorty.ui.pages.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.ui.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {


    private val searchStateFlow: MutableSharedFlow<CharactersResponse?> =
        MutableSharedFlow()
    val _searchStateFlow: SharedFlow<CharactersResponse?> = searchStateFlow

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    private val toastMessageObserver: MutableLiveData<String> = MutableLiveData()


    fun fetchSearch(text: String ,status:String) {
        viewModelScope.launch {
            repo.getSearch(text , status).collectLatest {
                when (it) {
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }
                    is ApiResponse.Success -> {
                        searchStateFlow.emit(it.data)
                        progressStateFlow.value = false

                    }
                    is ApiResponse.Failure -> {
                        try {
                            val errorResponse = Gson().fromJson(
                                it.data!!.errorBody()!!.string(),
                                CharactersResponse::class.java
                            )
                            searchStateFlow.emit(errorResponse)
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