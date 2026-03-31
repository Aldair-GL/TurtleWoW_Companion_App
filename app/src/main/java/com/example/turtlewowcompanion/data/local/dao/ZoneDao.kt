package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.ZoneCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZoneDao {
    @Query("SELECT * FROM zones ORDER BY name")
    fun observeAll(): Flow<List<ZoneCacheEntity>>

    @Query("SELECT * FROM zones WHERE id = :id")
    suspend fun getById(id: Long): ZoneCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(zones: List<ZoneCacheEntity>)

    @Query("DELETE FROM zones")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM zones")
    suspend fun count(): Int
}

