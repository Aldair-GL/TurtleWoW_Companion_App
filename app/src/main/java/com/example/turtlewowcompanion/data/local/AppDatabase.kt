package com.example.turtlewowcompanion.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.turtlewowcompanion.data.local.dao.*
import com.example.turtlewowcompanion.data.local.entity.*

@Database(
    entities = [
        ZoneCacheEntity::class,
        QuestCacheEntity::class,
        NpcCacheEntity::class,
        FavoriteEntity::class,
        SearchHistoryEntity::class,
        ProfessionCacheEntity::class,
        ItemCacheEntity::class,
        BossCacheEntity::class,
        RaceCacheEntity::class,
        ClassCacheEntity::class,
        UserProfileEntity::class,
        UserCharacterEntity::class,
        DungeonProgressEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun zoneDao(): ZoneDao
    abstract fun questDao(): QuestDao
    abstract fun npcDao(): NpcDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun professionDao(): ProfessionDao
    abstract fun itemDao(): ItemDao
    abstract fun bossDao(): BossDao
    abstract fun raceDao(): RaceDao
    abstract fun classDao(): ClassDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun userCharacterDao(): UserCharacterDao
    abstract fun dungeonProgressDao(): DungeonProgressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "turtle_wow_companion.db"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}
