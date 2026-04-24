package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.RaceCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {
    @Query("SELECT * FROM races ORDER BY name")
    fun observeAll(): Flow<List<RaceCacheEntity>>

    @Query("SELECT * FROM races WHERE id = :id")
    suspend fun getById(id: Long): RaceCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(races: List<RaceCacheEntity>)

    @Query("SELECT COUNT(*) FROM races")
    suspend fun count(): Int
}

