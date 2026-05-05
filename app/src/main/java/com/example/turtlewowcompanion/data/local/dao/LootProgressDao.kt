package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.LootProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LootProgressDao {
    @Query("SELECT * FROM loot_progress WHERE userId = :userId ORDER BY obtainedAt DESC")
    fun observeByUser(userId: Long): Flow<List<LootProgressEntity>>

    @Query("SELECT * FROM loot_progress WHERE userId = :userId AND itemName = :itemName AND bossId = :bossId LIMIT 1")
    suspend fun getByUserItemAndBoss(userId: Long, itemName: String, bossId: Long): LootProgressEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: LootProgressEntity): Long

    @Query("DELETE FROM loot_progress WHERE userId = :userId AND itemName = :itemName AND bossId = :bossId")
    suspend fun deleteByUserItemAndBoss(userId: Long, itemName: String, bossId: Long)

    @Query("SELECT COUNT(*) FROM loot_progress WHERE userId = :userId")
    suspend fun countByUser(userId: Long): Int

    @Query("DELETE FROM loot_progress WHERE userId = :userId")
    suspend fun deleteAllForUser(userId: Long)
}


