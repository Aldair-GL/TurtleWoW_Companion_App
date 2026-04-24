package com.example.turtlewowcompanion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.turtlewowcompanion.data.local.entity.UserCharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCharacterDao {
    @Query("SELECT * FROM user_characters WHERE userId = :userId ORDER BY createdAt DESC")
    fun observeByUser(userId: Long): Flow<List<UserCharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: UserCharacterEntity): Long

    @Update
    suspend fun update(character: UserCharacterEntity)

    @Delete
    suspend fun delete(character: UserCharacterEntity)

    @Query("DELETE FROM user_characters WHERE id = :id")
    suspend fun deleteById(id: Long)
}

