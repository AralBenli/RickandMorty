package com.example.rickandmorty.models

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("info")
    val info: CharInfo,
    @SerializedName("results")
    val results: List<CharResult>
)

data class CharInfo(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("prev")
    val prev: Any
)

data class CharResult(
    @SerializedName("created")
    val created: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: CharLocation,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: CharOrigin,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

data class CharLocation(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class CharOrigin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)