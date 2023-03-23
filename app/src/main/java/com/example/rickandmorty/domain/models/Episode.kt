package com.example.rickandmorty.domain.models


data class Episode(
    val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val seasonNumber: Int = 0,
    val episodeNumber: Int = 0,
    val characters: List<Characters> = emptyList()
) {
    fun getFormattedSeason(): String {
        return "Season $seasonNumber Episode $episodeNumber"
    }

    fun getFormattedSeasonTruncated(): String {
        return "S.$seasonNumber E.$episodeNumber"
    }
}