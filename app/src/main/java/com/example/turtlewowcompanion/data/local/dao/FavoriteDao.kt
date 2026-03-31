package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.turtlewowcompanion.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE refId = :refId AND type = :type LIMIT 1")
    suspend fun findByRefAndType(refId: Long, type: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE refId = :refId AND type = :type")
    suspend fun deleteByRefAndType(refId: Long, type: String)

    @Query("DELETE FROM favorites")
    suspend fun deleteAll()
}

