package com.example.rickandmorty.di

import com.example.rickandmorty.api.RickAndMortyApi
import com.example.rickandmorty.repositories.IRickAndMortyRepository
import com.example.rickandmorty.repositories.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideRickAndMortyRepository(rickAndMortyApi: RickAndMortyApi): IRickAndMortyRepository {
        return RickAndMortyRepository(rickAndMortyApi)
    }
}