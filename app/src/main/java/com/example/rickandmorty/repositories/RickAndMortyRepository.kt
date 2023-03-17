package com.example.rickandmorty.repositories

import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.di.ApiResponse
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.utils.result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RickAndMortyRepository(
    private val api : RickAndMortyApi
    ): IRickAndMortyRepository {

    override fun getCharacters(): Flow<ApiResponse<CharactersResponse?>> =
       result {
           api.getCharacters()
       }.flowOn(Dispatchers.IO)

}