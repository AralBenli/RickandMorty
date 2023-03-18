package com.example.rickandmorty.models

import com.google.gson.annotations.SerializedName

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