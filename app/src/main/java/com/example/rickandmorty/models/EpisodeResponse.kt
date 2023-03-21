package com.example.rickandmorty.models

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("info")
    val info: CharInfo? = CharInfo(),
    @SerializedName("results")
    val results: List<EpisodeItem>? = emptyList()
)



data class EpisodeItem(
    @SerializedName("air_date")
    val airDate: String? = "",
    @SerializedName("characters")
    val characters: List<String>? = listOf(),
    @SerializedName("created")
    val created: String? = "",
    @SerializedName("episode")
    val episode: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)

