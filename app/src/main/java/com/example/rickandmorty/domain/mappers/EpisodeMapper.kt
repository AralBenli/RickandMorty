package com.example.rickandmorty.domain.mappers

import com.example.rickandmorty.domain.models.Episode
import com.example.rickandmorty.response.CharacterItem
import com.example.rickandmorty.response.CharactersResponse
import com.example.rickandmorty.response.EpisodeItem

object EpisodeMapper {

    fun buildFrom(
        networkEpisode: EpisodeItem,
        networkCharacters: List<CharacterItem> = emptyList()
    ): Episode {
        return Episode(
            id = networkEpisode.id!!,
            name = networkEpisode.name!!,
            airDate = networkEpisode.airDate!!,
            seasonNumber = getSeasonNumberFromEpisodeString(networkEpisode.episode!!),
            episodeNumber = getEpisodeNumberFromEpisodeString(networkEpisode.episode),
            characters = networkCharacters.map {
                CharacterMapper.buildFrom(it)
            }
        )
    }

    private fun getSeasonNumberFromEpisodeString(episode: String): Int {
        val endIndex = episode.indexOfFirst { it.equals('e', true) }
        if (endIndex == -1) {
            return 0
        }
        return episode.substring(1, endIndex).toIntOrNull() ?: 0
    }

    private fun getEpisodeNumberFromEpisodeString(episode: String): Int {
        val startIndex = episode.indexOfFirst { it.equals('e', true) }
        if (startIndex == -1) {
            return 0
        }
        return episode.substring(startIndex + 1).toIntOrNull() ?: 0
    }
}