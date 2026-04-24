package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.ItemCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY name")
    fun observeAll(): Flow<List<ItemCacheEntity>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getById(id: Long): ItemCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<ItemCacheEntity>)

    @Query("SELECT * FROM items WHERE quality = :quality ORDER BY name")
    fun observeByQuality(quality: String): Flow<List<ItemCacheEntity>>

    @Query("SELECT * FROM items WHERE type = :type ORDER BY name")
    fun observeByType(type: String): Flow<List<ItemCacheEntity>>

    @Query("SELECT COUNT(*) FROM items")
    suspend fun count(): Int
}

