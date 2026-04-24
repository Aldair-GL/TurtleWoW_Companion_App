package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.DungeonProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DungeonProgressDao {
    @Query("SELECT * FROM dungeon_progress WHERE userId = :userId ORDER BY zoneName")
    fun observeByUser(userId: Long): Flow<List<DungeonProgressEntity>>

    @Query("SELECT * FROM dungeon_progress WHERE userId = :userId AND zoneId = :zoneId LIMIT 1")
    suspend fun getByUserAndZone(userId: Long, zoneId: Long): DungeonProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: DungeonProgressEntity)

    @Query("UPDATE dungeon_progress SET completed = :completed, completedAt = :completedAt WHERE userId = :userId AND zoneId = :zoneId")
    suspend fun updateCompletion(userId: Long, zoneId: Long, completed: Boolean, completedAt: Long?)

    @Query("SELECT COUNT(*) FROM dungeon_progress WHERE userId = :userId AND completed = 1")
    suspend fun countCompleted(userId: Long): Int
}

