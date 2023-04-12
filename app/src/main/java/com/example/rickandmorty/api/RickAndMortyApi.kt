package com.example.rickandmorty.api

import com.example.rickandmorty.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @Headers("Cache-Control: max-age=86400")
    @GET("character/")
    suspend fun getCharacters(
        @Query("page") pageIndex: Int? = null
    ): Response<CharactersResponse>

    @Headers("Cache-Control: max-age=86400")
    @GET("location/")
    suspend fun getLocations(
        @Query("page") pageIndex: Int? = null
    ): Response<LocationResponse>

    @Headers("Cache-Control: max-age=86400")
    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") pageIndex: Int? = null
    ): Response<EpisodeResponse>


    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<CharacterItem>

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int
    ): Response<EpisodeItem>

    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): Response<LocationItem>

    @GET("character/")
    suspend fun getSearch(
        @Query("name") name: String,
        @Query("status") status: String
    ): Response<CharactersResponse>

    @GET("episode/{episodes}")
    suspend fun getCharacterEpisodes(
        @Path("episodes") episodes: String
    ): Response<List<EpisodeItem>>

    @GET("character/{list}")
    suspend fun getMoreCharactersThanOne(
        @Path("list") characterList: List<String>
    ): Response<List<CharacterItem>>

}