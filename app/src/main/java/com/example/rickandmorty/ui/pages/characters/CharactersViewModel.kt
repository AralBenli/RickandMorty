package com.example.rickandmorty.ui.pages.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.ui.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repo: IRickAndMortyRepository,
) : BaseViewModel() {

    private val characterStateFlow: MutableSharedFlow<CharactersResponse?> =
        MutableSharedFlow()
    val _characterStateFlow: SharedFlow<CharactersResponse?> = characterStateFlow

    private val progressStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val _progressStateFlow: StateFlow<Boolean> = progressStateFlow

    private val toastMessageObserver: MutableLiveData<String> = MutableLiveData()


    fun fetchCharacter(){
        viewModelScope.launch {
            repo.getCharacters().collectLatest {
                when(it){
                    is ApiResponse.Progress -> {
                        progressStateFlow.value = true
                    }
                    is ApiResponse.Success -> {
                        characterStateFlow.emit(it.data)
                    }
                    is ApiResponse.Failure -> {
                        try {
                            val errorResponse = Gson().fromJson(
                                it.data!!.errorBody()!!.string(),
                                CharactersResponse::class.java
                            )
                            characterStateFlow.emit(errorResponse)
                        } catch (e: Exception) {
                            toastMessageObserver.setValue("Bağlantı sırasında bir hata oluştu.")
                        }
                        progressStateFlow.value = false

                    }
                }
            }
        }
    }
}