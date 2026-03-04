package com.example.turtlewowcompanion.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GuideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<GuideEntryEntity>)

    @Query("SELECT * FROM guide_entries WHERE category = :category ORDER BY title")
    fun observeByCategory(category: String): Flow<List<GuideEntryEntity>>

    @Query("SELECT COUNT(*) FROM guide_entries WHERE category = :category")
    suspend fun countByCategory(category: String): Int
}
