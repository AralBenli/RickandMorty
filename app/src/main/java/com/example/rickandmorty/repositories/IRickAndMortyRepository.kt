package com.example.rickandmorty.repositories

import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.models.CharactersResponse
import kotlinx.coroutines.flow.Flow

interface IRickAndMortyRepository {

    fun getCharacters(
    ): Flow<ApiResponse<CharactersResponse?>>

}