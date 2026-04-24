package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.ProfessionCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfessionDao {
    @Query("SELECT * FROM professions ORDER BY name")
    fun observeAll(): Flow<List<ProfessionCacheEntity>>

    @Query("SELECT * FROM professions WHERE id = :id")
    suspend fun getById(id: Long): ProfessionCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(professions: List<ProfessionCacheEntity>)

    @Query("SELECT COUNT(*) FROM professions")
    suspend fun count(): Int
}

