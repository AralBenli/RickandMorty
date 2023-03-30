package com.example.rickandmorty.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class EpisodeResponse(
    @SerializedName("info")
    val info: Info? = Info(),
    @SerializedName("results")
    val results: List<EpisodeItem>? = emptyList()
): Parcelable



@Parcelize
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
) : Parcelable

