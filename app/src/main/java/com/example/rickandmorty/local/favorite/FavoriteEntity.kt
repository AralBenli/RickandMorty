package com.example.rickandmorty.local.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.response.CharLocation
import com.example.rickandmorty.response.CharOrigin
import com.example.rickandmorty.response.CharacterItem
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_entity")
data class FavoriteEntity(
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
)

fun FavoriteEntity.toCharacterItem(): CharacterItem {
    return CharacterItem(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        image = image,
        isFavorite = isFavorite
    )
}
fun CharacterItem.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin,
        location = location,
        image = image,
        isFavorite = isFavorite
    )
}