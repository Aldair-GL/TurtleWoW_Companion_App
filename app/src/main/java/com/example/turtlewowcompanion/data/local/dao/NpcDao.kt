package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.NpcCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NpcDao {
    @Query("SELECT * FROM npcs ORDER BY name")
    fun observeAll(): Flow<List<NpcCacheEntity>>

    @Query("SELECT * FROM npcs WHERE id = :id")
    suspend fun getById(id: Long): NpcCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(npcs: List<NpcCacheEntity>)

    @Query("DELETE FROM npcs")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM npcs")
    suspend fun count(): Int
}

