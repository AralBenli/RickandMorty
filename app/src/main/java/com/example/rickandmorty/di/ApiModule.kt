package com.example.rickandmorty.di

import com.example.rickandmorty.api.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideRickAndMortyApi(@Named("provideRetrofit") retrofit: Retrofit) : RickAndMortyApi {
        return retrofit.create(RickAndMortyApi::class.java)
    }
}