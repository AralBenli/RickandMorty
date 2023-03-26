package com.example.rickandmorty.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RickAndMortyApplication : Application(){

    override fun onCreate() {
        super.onCreate()

    }

}


