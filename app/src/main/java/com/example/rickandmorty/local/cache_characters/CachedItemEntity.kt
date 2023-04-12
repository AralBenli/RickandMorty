package com.example.rickandmorty.local.cache_characters

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.response.CharLocation
import com.example.rickandmorty.response.CharOrigin
import com.example.rickandmorty.response.CharacterItem
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cached_characters")
data class CachedCharacterEntity(
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
)

fun mapToCachedCharacterEntity(characters: List<CharacterItem>): List<CachedCharacterEntity> {
    return characters.map {
        CachedCharacterEntity(
            id = it.id,
            name = it.name,
            status = it.status,
            species = it.species,
            type = it.type,
            gender = it.gender,
            created = it.created,
            episode = it.episode,
            image = it.image,
            location = it.location,
            origin = it.origin,
            url = it.url
        )
    }
}
