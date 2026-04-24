package com.example.turtlewowcompanion.di

import android.content.Context
import com.example.turtlewowcompanion.data.local.AppDatabase
import com.example.turtlewowcompanion.data.remote.NetworkModule
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.data.repository.BossRepository
import com.example.turtlewowcompanion.data.repository.FavoriteRepository
import com.example.turtlewowcompanion.data.repository.ItemRepository
import com.example.turtlewowcompanion.data.repository.NpcRepository
import com.example.turtlewowcompanion.data.repository.ProfessionRepository
import com.example.turtlewowcompanion.data.repository.QuestRepository
import com.example.turtlewowcompanion.data.repository.RaceRepository
import com.example.turtlewowcompanion.data.repository.SearchRepository
import com.example.turtlewowcompanion.data.repository.SettingsRepository
import com.example.turtlewowcompanion.data.repository.WowClassRepository
import com.example.turtlewowcompanion.data.repository.ZoneRepository
import com.example.turtlewowcompanion.data.repository.dataStore

/** Contenedor manual de dependencias. Inicializado en TurtleWoWApplication. */
class AppContainer(context: Context) {

    // Data sources
    private val database: AppDatabase = AppDatabase.getInstance(context)
    private val api: TurtleWowApi = NetworkModule.api

    // Repositories
    val zoneRepository: ZoneRepository = ZoneRepository(api, database.zoneDao())
    val raceRepository: RaceRepository = RaceRepository(api, database.raceDao())
    val classRepository: WowClassRepository = WowClassRepository(api, database.classDao())
    val bossRepository: BossRepository = BossRepository(api, database.bossDao())
    val professionRepository: ProfessionRepository = ProfessionRepository(api, database.professionDao())
    val itemRepository: ItemRepository = ItemRepository(api, database.itemDao())
    val questRepository: QuestRepository = QuestRepository(api, database.questDao())
    val npcRepository: NpcRepository = NpcRepository(api, database.npcDao())
    val favoriteRepository: FavoriteRepository = FavoriteRepository(database.favoriteDao())
    val searchRepository: SearchRepository = SearchRepository(api, database.searchHistoryDao(), database.zoneDao())
    val settingsRepository: SettingsRepository = SettingsRepository(context.dataStore)
}
