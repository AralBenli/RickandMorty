package com.example.rickandmorty.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.local.favorite.FavoriteEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class CharactersResponse(
    @SerializedName("info")
    val info: Info? = Info(),
    @SerializedName("results")
    val results: List<CharacterItem>? = emptyList()
)

@Parcelize
data class CharacterItem(
    @PrimaryKey @field:SerializedName("id")
    val id: Int? = 0,
    @SerializedName("created")
    val created: String? = "",
    @SerializedName("episode")
    val episode: List<String>? = listOf(),
    @SerializedName("gender")
    val gender: String? = "",
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("location")
    val location: CharLocation? = CharLocation(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("origin")
    val origin: CharOrigin? = CharOrigin(),
    @SerializedName("species")
    val species: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("isFavorite")
    var isFavorite: Boolean

) : Parcelable

@Parcelize
data class FavoriteState(
    val characterList: List<CharacterItem> = emptyList(),
    val isError: Boolean = false
) : Parcelable

@Parcelize

data class CharLocation(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
) : Parcelable

@Parcelize

data class CharOrigin(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
) : Parcelable


