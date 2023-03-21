package com.example.rickandmorty.models

import com.google.gson.annotations.SerializedName

data class CharInfo(
    @SerializedName("pages")
    val pages: Int? = 0,
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: Any? = null
)