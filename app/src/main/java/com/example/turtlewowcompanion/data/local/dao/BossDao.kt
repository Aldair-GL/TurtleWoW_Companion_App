package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.BossCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BossDao {
    @Query("SELECT * FROM bosses WHERE zoneId = :zoneId ORDER BY name")
    fun observeByZone(zoneId: Long): Flow<List<BossCacheEntity>>

    @Query("SELECT * FROM bosses WHERE id = :id")
    suspend fun getById(id: Long): BossCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(bosses: List<BossCacheEntity>)

    @Query("SELECT COUNT(*) FROM bosses WHERE zoneId = :zoneId")
    suspend fun countByZone(zoneId: Long): Int
}

