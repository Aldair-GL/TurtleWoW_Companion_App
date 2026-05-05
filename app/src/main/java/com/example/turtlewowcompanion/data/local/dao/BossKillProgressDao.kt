package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.BossKillProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BossKillProgressDao {
    @Query("SELECT * FROM boss_kill_progress WHERE userId = :userId ORDER BY killedAt DESC")
    fun observeByUser(userId: Long): Flow<List<BossKillProgressEntity>>

    @Query("SELECT * FROM boss_kill_progress WHERE userId = :userId AND bossId = :bossId LIMIT 1")
    suspend fun getByUserAndBoss(userId: Long, bossId: Long): BossKillProgressEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: BossKillProgressEntity): Long

    @Query("DELETE FROM boss_kill_progress WHERE userId = :userId AND bossId = :bossId")
    suspend fun deleteByUserAndBoss(userId: Long, bossId: Long)

    @Query("SELECT COUNT(*) FROM boss_kill_progress WHERE userId = :userId")
    suspend fun countByUser(userId: Long): Int

    @Query("DELETE FROM boss_kill_progress WHERE userId = :userId")
    suspend fun deleteAllForUser(userId: Long)
}


