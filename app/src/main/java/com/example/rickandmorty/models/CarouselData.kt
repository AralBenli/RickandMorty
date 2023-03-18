package com.example.rickandmorty.models

import com.example.rickandmorty.R

data class CarouselData(
    val id: Int,
    val image: Int,
    val name: String
)

val scrollView = listOf(
    CarouselData(1, R.drawable.rick_sanchez, "Rick Sanchez"),
    CarouselData(2, R.drawable.morty_smith, "Morty Smith"),
    CarouselData(3, R.drawable.beth_smith, "Beth Smith"),
    CarouselData(4, R.drawable.summer_smith, "Summer Smith"),
    CarouselData(5, R.drawable.jerry_smith, "Jerry Smith"),
    )