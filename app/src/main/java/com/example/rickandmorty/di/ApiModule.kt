package com.example.rickandmorty.di

import android.app.Application
import androidx.room.Room
import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.local.RickAndMortyRoomDatabase
import com.example.rickandmorty.repositories.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideRickAndMortyApi(@Named("provideRetrofit") retrofit: Retrofit): RickAndMortyApi {
        return retrofit.create(RickAndMortyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(app: Application): RickAndMortyRoomDatabase {
        return Room.databaseBuilder(app, RickAndMortyRoomDatabase::class.java, "FavoriteDatabase")
            .build()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object CoroutineScopeModule {
        @Provides
        @Singleton
        fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        }
    }


