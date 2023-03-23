package com.example.rickandmorty.domain.mappers

import com.example.rickandmorty.domain.models.Characters
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.response.EpisodeItem

object CharacterMapper {

    fun buildFrom(
        response: CharacterItem ,
        episodes: List<EpisodeItem> = emptyList()
    ): Characters {
        return Characters(
            episodeList = episodes.map {
                EpisodeMapper.buildFrom(it)
            },
            gender = response.gender!!,
            id = response.id!!,
            image = response.image!!,
            location = Characters.Location(
                name = response.location?.name!!,
                url = response.location.url!!
            ),
            name = response.name!!,
            origin = Characters.Origin(
                name = response.origin?.name!!,
                url = response.origin.url!!
            ),
            species = response.species!!,
            status = response.status!!
        )
    }
}