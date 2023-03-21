package com.example.rickandmorty.api

import com.example.rickandmorty.models.CharacterItem
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.models.EpisodeResponse
import com.example.rickandmorty.models.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") pageIndex : Int
    ): Response<CharactersResponse>
    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id")id:Int
    ): Response<CharacterItem>

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") pageIndex : Int
    ):Response<EpisodeResponse>

    @GET("location")
    suspend fun getLocations(
        @Query("page") pageIndex : Int
    ):Response<LocationResponse>


    @GET("character/")
    suspend fun getSearch(
        @Query("name") name :String,
        ): Response<CharactersResponse>
}