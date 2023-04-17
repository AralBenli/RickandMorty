package com.example.rickandmorty.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmorty.local.cache_characters.CachedCharacterDao
import com.example.rickandmorty.local.cache_characters.CachedCharacterEntity
import com.example.rickandmorty.local.favorite.FavoriteCharacterDao
import com.example.rickandmorty.local.favorite.FavoriteEntity
import com.example.rickandmorty.local.remote_keys.RemoteKeysDao
import com.example.rickandmorty.local.remote_keys.RemoteKeysEntity
import com.example.rickandmorty.utils.ConverterForRoom


@Database(
    entities = [
        FavoriteEntity::class,
        CachedCharacterEntity::class,
        RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConverterForRoom::class)
abstract class RickAndMortyRoomDatabase : RoomDatabase() {

    abstract val cacheDao: CachedCharacterDao
    abstract val favoriteDao: FavoriteCharacterDao
    abstract val remoteKeysDao: RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: RickAndMortyRoomDatabase? = null

        fun getDatabase(context: Context): RickAndMortyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RickAndMortyRoomDatabase::class.java, "RickAndMortyRoomDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        /*private val migration1To2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE CachedCharacterEntity ADD COLUMN imageUrl TEXT DEFAULT ''")
            }
        }*/
    }
}
