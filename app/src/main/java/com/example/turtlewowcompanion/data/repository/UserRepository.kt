package com.example.turtlewowcompanion.data.repository


import com.example.turtlewowcompanion.data.local.dao.BossKillProgressDao
import com.example.turtlewowcompanion.data.local.dao.DungeonProgressDao
import com.example.turtlewowcompanion.data.local.dao.LootProgressDao
import com.example.turtlewowcompanion.data.local.dao.UserCharacterDao
import com.example.turtlewowcompanion.data.local.dao.UserProfileDao
import com.example.turtlewowcompanion.data.local.entity.BossKillProgressEntity
import com.example.turtlewowcompanion.data.local.entity.DungeonProgressEntity
import com.example.turtlewowcompanion.data.local.entity.LootProgressEntity
import com.example.turtlewowcompanion.data.local.entity.UserCharacterEntity
import com.example.turtlewowcompanion.data.local.entity.UserProfileEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.data.remote.dto.AuthRequestDto
import com.example.turtlewowcompanion.data.remote.dto.BossKillCreateDto
import com.example.turtlewowcompanion.data.remote.dto.CharacterCreateRequestDto
import com.example.turtlewowcompanion.data.remote.dto.DungeonProgressUpdateDto
import com.example.turtlewowcompanion.data.remote.dto.LootCreateDto
import com.example.turtlewowcompanion.data.remote.dto.LootDeleteDto
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest

class UserRepository(
    private val userDao: UserProfileDao,
    private val characterDao: UserCharacterDao,
    private val dungeonProgressDao: DungeonProgressDao,
    private val bossKillProgressDao: BossKillProgressDao,
    private val lootProgressDao: LootProgressDao,
    private val api: TurtleWowApi? = null
) {

    companion object {
        const val MAX_CHARACTERS_PER_USER = 6
    }

    // ── Autenticación (backend + fallback Room) ─────────────────────────

    suspend fun register(username: String, password: String): Result<Long> {
        val cleanUser = username.trim()
        if (cleanUser.length < 3) return Result.failure(Exception("El nombre debe tener al menos 3 caracteres"))
        if (password.length < 4) return Result.failure(Exception("La contraseña debe tener al menos 4 caracteres"))

        if (api != null) {
            try {
                val remoteUser = api.register(AuthRequestDto(cleanUser, password))
                syncLocalProfile(remoteUser.id, remoteUser.username, password)
                return Result.success(remoteUser.id)
            } catch (e: retrofit2.HttpException) {
                val msg = when (e.code()) {
                    409 -> "Ese nombre de usuario ya está en uso"
                    400 -> "Datos inválidos. Revisa usuario y contraseña."
                    404 -> "El servidor aún no expone el registro. Reintenta en unos segundos."
                    in 500..599 -> "El servidor está iniciando o tiene un fallo (${e.code()}). Inténtalo en unos segundos."
                    else -> "Error del servidor: ${e.code()}"
                }
                return Result.failure(Exception(msg))
            } catch (e: java.net.SocketTimeoutException) {
                return Result.failure(Exception("El servidor tarda demasiado en responder. Vuelve a intentarlo."))
            } catch (e: java.io.IOException) {
                return Result.failure(Exception("Sin conexión con el servidor. Comprueba tu internet."))
            }
        }

        val existing = userDao.findByUsername(cleanUser)
        if (existing != null) return Result.failure(Exception("Ese nombre de usuario ya está en uso"))
        val hash = hashPassword(password)
        val id = userDao.insert(UserProfileEntity(username = cleanUser, passwordHash = hash))
        return Result.success(id)
    }

    suspend fun login(username: String, password: String): Result<UserProfileEntity> {
        val cleanUser = username.trim()
        if (api != null) {
            try {
                val remoteUser = api.login(AuthRequestDto(cleanUser, password))
                syncLocalProfile(remoteUser.id, remoteUser.username, password)
                val saved = userDao.getById(remoteUser.id)
                if (saved != null) return Result.success(saved)
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 404) {
                    val hash = hashPassword(password)
                    userDao.login(cleanUser, hash)?.let { return Result.success(it) }
                    return Result.failure(Exception("El servidor aún no expone el login. Reintenta en unos segundos."))
                }
                val msg = when (e.code()) {
                    401 -> "Usuario o contraseña incorrectos"
                    in 500..599 -> "El servidor está iniciando o tiene un fallo (${e.code()}). Inténtalo en unos segundos."
                    else -> "Error del servidor: ${e.code()}"
                }
                return Result.failure(Exception(msg))
            } catch (e: java.net.SocketTimeoutException) {
                return Result.failure(Exception("El servidor tarda demasiado en responder. Vuelve a intentarlo."))
            } catch (e: java.io.IOException) {
                // Sin conexión: probamos fallback local
            }
        }

        val hash = hashPassword(password)
        val user = userDao.login(cleanUser, hash)
            ?: return Result.failure(Exception("Usuario o contraseña incorrectos"))
        return Result.success(user)
    }

    /**
     * Asegura que en Room hay una fila user_profiles con el id devuelto por el backend.
     * Si existía un perfil con el mismo username pero distinto id, lo borra (cascade limpia
     * datos huérfanos) y reinserta con el id correcto. Esto evita FK violations al insertar
     * entidades hijas que referencian userId.
     */
    private suspend fun syncLocalProfile(remoteId: Long, remoteUsername: String, password: String) {
        val hash = hashPassword(password)
        val existing = userDao.findByUsername(remoteUsername)
        when {
            existing == null ->
                userDao.insert(UserProfileEntity(id = remoteId, username = remoteUsername, passwordHash = hash))
            existing.id != remoteId -> {
                userDao.deleteById(existing.id)
                userDao.insert(UserProfileEntity(id = remoteId, username = remoteUsername, passwordHash = hash))
            }
            existing.passwordHash != hash ->
                userDao.update(existing.copy(passwordHash = hash))
        }
    }

    suspend fun getUserById(id: Long): UserProfileEntity? = userDao.getById(id)

    // ── Personajes ──────────────────────────────────────────────────────────

    fun observeCharacters(userId: Long): Flow<List<UserCharacterEntity>> =
        characterDao.observeByUser(userId)

    suspend fun countCharacters(userId: Long): Int = characterDao.countByUser(userId)

    suspend fun addCharacter(
        userId: Long,
        name: String,
        raceName: String,
        className: String,
        level: Int
    ): Result<Long> {
        val cleanName = name.trim()
        if (cleanName.isBlank()) return Result.failure(Exception("El nombre no puede estar vacío"))
        if (userDao.getById(userId) == null) {
            return Result.failure(Exception("Tu sesión no es válida. Vuelve a iniciar sesión."))
        }
        val safeLevel = level.coerceIn(1, 60)

        // Remote-first
        if (api != null) {
            try {
                val remote = api.createCharacter(
                    userId,
                    CharacterCreateRequestDto(cleanName, raceName, className, safeLevel)
                )
                return try {
                    val newId = characterDao.insert(
                        UserCharacterEntity(
                            id = remote.id,
                            userId = userId,
                            name = remote.name,
                            raceName = remote.raceName,
                            className = remote.className,
                            level = remote.level
                        )
                    )
                    Result.success(if (newId > 0) newId else remote.id)
                } catch (e: Exception) {
                    Result.success(remote.id) // backend ya lo guardó; Room reintentará en el próximo pull
                }
            } catch (e: retrofit2.HttpException) {
                val msg = when (e.code()) {
                    409 -> "Has alcanzado el máximo de $MAX_CHARACTERS_PER_USER personajes"
                    400 -> "Datos del personaje inválidos"
                    404 -> "Tu sesión no es válida. Vuelve a iniciar sesión."
                    in 500..599 -> "El servidor falló al crear el personaje (${e.code()}). Revisa los logs del backend."
                    else -> "Error del servidor: ${e.code()}"
                }
                return Result.failure(Exception(msg))
            } catch (_: java.io.IOException) {
                // Sin conexión: caemos a Room (modo offline)
            }
        }

        if (characterDao.countByUser(userId) >= MAX_CHARACTERS_PER_USER) {
            return Result.failure(Exception("Has alcanzado el máximo de $MAX_CHARACTERS_PER_USER personajes"))
        }
        return try {
            val id = characterDao.insert(
                UserCharacterEntity(
                    userId = userId,
                    name = cleanName,
                    raceName = raceName,
                    className = className,
                    level = safeLevel
                )
            )
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(Exception("No se pudo crear el personaje: ${e.message ?: "error desconocido"}"))
        }
    }

    suspend fun updateCharacter(character: UserCharacterEntity) = characterDao.update(character)

    suspend fun deleteCharacter(userId: Long, id: Long) {
        if (api != null) {
            try {
                api.deleteCharacter(userId, id)
            } catch (_: java.io.IOException) {
                // sin red: borramos solo local; un pull posterior lo reconciliará
            } catch (_: retrofit2.HttpException) {
                // 404 → ya no existe en backend; seguimos
            }
        }
        characterDao.deleteById(id)
    }

    @Deprecated("Usar deleteCharacter(userId, id)", ReplaceWith("deleteCharacter(userId, id)"))
    suspend fun deleteCharacter(id: Long) = characterDao.deleteById(id)

    // ── Progreso de mazmorras ─────────────────────────────────────────────

    fun observeDungeonProgress(userId: Long): Flow<List<DungeonProgressEntity>> =
        dungeonProgressDao.observeByUser(userId)

    suspend fun toggleDungeonCompletion(userId: Long, zoneId: Long, zoneName: String) {
        if (userDao.getById(userId) == null) return
        val existing = dungeonProgressDao.getByUserAndZone(userId, zoneId)
        val newCompleted = existing?.completed != true

        if (api != null) {
            try {
                api.upsertDungeonProgress(
                    userId, zoneId, DungeonProgressUpdateDto(completed = newCompleted, zoneName = zoneName)
                )
            } catch (_: java.io.IOException) {
                // offline: persistimos local y seguimos
            } catch (_: retrofit2.HttpException) {
                // backend rechazó: persistimos local igualmente para no perder el toggle
            }
        }

        if (existing == null) {
            dungeonProgressDao.upsert(
                DungeonProgressEntity(
                    userId = userId, zoneId = zoneId, zoneName = zoneName,
                    completed = newCompleted,
                    completedAt = if (newCompleted) System.currentTimeMillis() else null
                )
            )
        } else {
            dungeonProgressDao.updateCompletion(
                userId, zoneId, newCompleted,
                if (newCompleted) System.currentTimeMillis() else null
            )
        }
    }

    suspend fun getCompletedDungeonCount(userId: Long): Int =
        dungeonProgressDao.countCompleted(userId)

    suspend fun isDungeonCompleted(userId: Long, zoneId: Long): Boolean {
        val entry = dungeonProgressDao.getByUserAndZone(userId, zoneId)
        return entry?.completed == true
    }

    // ── Progreso de jefes derrotados ─────────────────────────────────────

    fun observeBossKills(userId: Long): Flow<List<BossKillProgressEntity>> =
        bossKillProgressDao.observeByUser(userId)

    suspend fun isBossKilled(userId: Long, bossId: Long): Boolean =
        bossKillProgressDao.getByUserAndBoss(userId, bossId) != null

    suspend fun toggleBossKill(userId: Long, bossId: Long, bossName: String, zoneName: String) {
        if (userDao.getById(userId) == null) return
        val existing = bossKillProgressDao.getByUserAndBoss(userId, bossId)

        if (api != null) {
            try {
                if (existing == null) api.createBossKill(userId, BossKillCreateDto(bossId, bossName, zoneName))
                else api.deleteBossKill(userId, bossId)
            } catch (_: java.io.IOException) {
                // offline
            } catch (_: retrofit2.HttpException) {
                // backend rechazó (409 si ya existe / 404 si no): mantenemos coherencia local
            }
        }

        if (existing == null) {
            bossKillProgressDao.insert(
                BossKillProgressEntity(
                    userId = userId, bossId = bossId, bossName = bossName, zoneName = zoneName
                )
            )
        } else {
            bossKillProgressDao.deleteByUserAndBoss(userId, bossId)
        }
    }

    suspend fun getBossKillCount(userId: Long): Int = bossKillProgressDao.countByUser(userId)

    // ── Progreso de loot conseguido ──────────────────────────────────────

    fun observeLoot(userId: Long): Flow<List<LootProgressEntity>> =
        lootProgressDao.observeByUser(userId)

    suspend fun isLootObtained(userId: Long, itemName: String, bossId: Long): Boolean =
        lootProgressDao.getByUserItemAndBoss(userId, itemName, bossId) != null

    suspend fun toggleLoot(
        userId: Long,
        itemName: String,
        itemQuality: String?,
        bossId: Long,
        bossName: String
    ) {
        if (userDao.getById(userId) == null) return
        val existing = lootProgressDao.getByUserItemAndBoss(userId, itemName, bossId)

        if (api != null) {
            try {
                if (existing == null) {
                    api.createLoot(userId, LootCreateDto(itemName, itemQuality, bossId, bossName))
                } else {
                    api.deleteLoot(userId, LootDeleteDto(itemName, bossId))
                }
            } catch (_: java.io.IOException) {
                // sin red: aplicamos el cambio solo en local
            } catch (_: retrofit2.HttpException) {
                // backend rechazó: mantenemos coherencia local
            }
        }

        if (existing == null) {
            lootProgressDao.insert(
                LootProgressEntity(
                    userId = userId,
                    itemName = itemName,
                    itemQuality = itemQuality,
                    bossId = bossId,
                    bossName = bossName
                )
            )
        } else {
            lootProgressDao.deleteByUserItemAndBoss(userId, itemName, bossId)
        }
    }

    suspend fun getLootCount(userId: Long): Int = lootProgressDao.countByUser(userId)

    /**
     * Pull completo desde backend → Room para un usuario. Se invoca tras login/registro
     * para garantizar que los datos del dispositivo coinciden con la verdad del servidor.
     * Si no hay red o el backend devuelve error, el método sale silenciosamente sin tocar Room.
     */
    suspend fun pullForUser(userId: Long) {
        val client = api ?: return
        if (userDao.getById(userId) == null) return
        try {
            val characters = client.getCharacters(userId)
            val dungeons = client.getDungeonProgress(userId)
            val kills = client.getBossKills(userId)
            val loot = client.getLoot(userId)

            characterDao.deleteAllForUser(userId)
            characters.forEach { c ->
                runCatching {
                    characterDao.insert(
                        UserCharacterEntity(
                            id = c.id,
                            userId = userId,
                            name = c.name,
                            raceName = c.raceName,
                            className = c.className,
                            level = c.level
                        )
                    )
                }
            }

            dungeonProgressDao.deleteAllForUser(userId)
            dungeons.forEach { d ->
                runCatching {
                    dungeonProgressDao.upsert(
                        DungeonProgressEntity(
                            id = d.id,
                            userId = userId,
                            zoneId = d.zoneId,
                            zoneName = d.zoneName,
                            completed = d.completed,
                            completedAt = null
                        )
                    )
                }
            }

            bossKillProgressDao.deleteAllForUser(userId)
            kills.forEach { k ->
                runCatching {
                    bossKillProgressDao.insert(
                        BossKillProgressEntity(
                            id = k.id,
                            userId = userId,
                            bossId = k.bossId,
                            bossName = k.bossName,
                            zoneName = k.zoneName
                        )
                    )
                }
            }

            lootProgressDao.deleteAllForUser(userId)
            loot.forEach { l ->
                runCatching {
                    lootProgressDao.insert(
                        LootProgressEntity(
                            id = l.id,
                            userId = userId,
                            itemName = l.itemName,
                            itemQuality = l.itemQuality,
                            bossId = l.bossId,
                            bossName = l.bossName
                        )
                    )
                }
            }
        } catch (_: Exception) {
            // sin red o error servidor: dejamos Room como esté; reintentar en el próximo arranque
        }
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}







