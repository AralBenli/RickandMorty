package com.example.rickandmorty.local.remote_keys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeysEntity: List<RemoteKeysEntity>?)

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Long): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT * FROM remote_keys ORDER BY repoId DESC LIMIT 1")
    suspend fun getLastRemoteKey(): RemoteKeysEntity?

}