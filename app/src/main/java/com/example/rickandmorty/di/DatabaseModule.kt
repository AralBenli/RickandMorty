
package com.example.rickandmorty.di


/**
 * Created by AralBenli on 17.04.2023.
 *//*

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesRoomDatabase(
        @ApplicationContext context: Context,
    ): RickAndMortyRoomDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyRoomDatabase::class.java,
            "RickAndMortyRoomDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesCachedCharactersDao(cachedCharacters: RickAndMortyRoomDatabase): CachedCharacterDao {
        return cachedCharacters.cacheDao()
    }

    @Provides
    fun providesFavDao(favoriteCharacterDao: RickAndMortyRoomDatabase): FavoriteCharacterDao {
        return favoriteCharacterDao.favoriteDao()
    }

    @Provides
    fun providesRemoteDao(remoteKeysDao: RickAndMortyRoomDatabase): RemoteKeysDao {
        return remoteKeysDao.remoteKeysDao()
    }

}*/
