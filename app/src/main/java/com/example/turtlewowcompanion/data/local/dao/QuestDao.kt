package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.QuestCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Query("SELECT * FROM quests ORDER BY title")
    fun observeAll(): Flow<List<QuestCacheEntity>>

    @Query("SELECT * FROM quests WHERE id = :id")
    suspend fun getById(id: Long): QuestCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(quests: List<QuestCacheEntity>)

    @Query("DELETE FROM quests")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM quests")
    suspend fun count(): Int
}

