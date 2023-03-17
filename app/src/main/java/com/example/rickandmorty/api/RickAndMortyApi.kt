package com.example.rickandmorty.api

import com.example.rickandmorty.models.CharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getCharacters(): Response<CharactersResponse>

}