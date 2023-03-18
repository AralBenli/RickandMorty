package com.example.rickandmorty.models

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")
    val info: CharInfo,
    @SerializedName("results")
    val results: List<LocationItem>
)

data class LocationItem(
    @SerializedName("created")
    val created: String,
    @SerializedName("dimension")
    val dimension: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("residents")
    val residents: List<String>,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)