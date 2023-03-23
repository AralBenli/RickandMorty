package com.example.rickandmorty.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationResponse(
    @SerializedName("info")
    val info: CharInfo? = CharInfo(),
    @SerializedName("results")
    val results: List<LocationItem>? = emptyList()
):Parcelable

@Parcelize
data class LocationItem(
    @SerializedName("created")
    val created: String? = "",
    @SerializedName("dimension")
    val dimension: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("residents")
    val residents: List<String>?,
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
): Parcelable