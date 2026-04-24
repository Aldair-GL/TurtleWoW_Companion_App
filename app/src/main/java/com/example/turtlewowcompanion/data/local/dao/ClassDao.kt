package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.ClassCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    @Query("SELECT * FROM classes ORDER BY name")
    fun observeAll(): Flow<List<ClassCacheEntity>>

    @Query("SELECT * FROM classes WHERE id = :id")
    suspend fun getById(id: Long): ClassCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(classes: List<ClassCacheEntity>)

    @Query("SELECT COUNT(*) FROM classes")
    suspend fun count(): Int
}

