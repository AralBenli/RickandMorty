package com.example.rickandmorty.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    @SerializedName("pages")
    val pages: Int? = 0,
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: String? = null
) : Parcelable