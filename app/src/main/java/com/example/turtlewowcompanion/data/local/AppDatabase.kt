package com.example.turtlewowcompanion.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.turtlewowcompanion.data.local.dao.BossDao
import com.example.turtlewowcompanion.data.local.dao.ClassDao
import com.example.turtlewowcompanion.data.local.dao.FavoriteDao
import com.example.turtlewowcompanion.data.local.dao.ItemDao
import com.example.turtlewowcompanion.data.local.dao.NpcDao
import com.example.turtlewowcompanion.data.local.dao.ProfessionDao
import com.example.turtlewowcompanion.data.local.dao.QuestDao
import com.example.turtlewowcompanion.data.local.dao.RaceDao
import com.example.turtlewowcompanion.data.local.dao.SearchHistoryDao
import com.example.turtlewowcompanion.data.local.dao.ZoneDao
import com.example.turtlewowcompanion.data.local.entity.BossCacheEntity
import com.example.turtlewowcompanion.data.local.entity.ClassCacheEntity
import com.example.turtlewowcompanion.data.local.entity.FavoriteEntity
import com.example.turtlewowcompanion.data.local.entity.ItemCacheEntity
import com.example.turtlewowcompanion.data.local.entity.NpcCacheEntity
import com.example.turtlewowcompanion.data.local.entity.ProfessionCacheEntity
import com.example.turtlewowcompanion.data.local.entity.QuestCacheEntity
import com.example.turtlewowcompanion.data.local.entity.RaceCacheEntity
import com.example.turtlewowcompanion.data.local.entity.SearchHistoryEntity
import com.example.turtlewowcompanion.data.local.entity.ZoneCacheEntity

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
        ClassCacheEntity::class
    ],
    version = 5,
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
