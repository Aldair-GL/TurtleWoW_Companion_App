package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.DungeonProgressDao
import com.example.turtlewowcompanion.data.local.dao.UserCharacterDao
import com.example.turtlewowcompanion.data.local.dao.UserProfileDao
import com.example.turtlewowcompanion.data.local.entity.DungeonProgressEntity
import com.example.turtlewowcompanion.data.local.entity.UserCharacterEntity
import com.example.turtlewowcompanion.data.local.entity.UserProfileEntity
import com.example.turtlewowcompanion.data.remote.TurtleWowApi
import com.example.turtlewowcompanion.data.remote.dto.AuthRequestDto
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest

class UserRepository(
    private val userDao: UserProfileDao,
    private val characterDao: UserCharacterDao,
    private val dungeonProgressDao: DungeonProgressDao,
    private val api: TurtleWowApi? = null
) {
    // ── Autenticación (backend + fallback Room) ─────────────────────────

    suspend fun register(username: String, password: String): Result<Long> {
        if (username.trim().length < 3) return Result.failure(Exception("El nombre debe tener al menos 3 caracteres"))
        if (password.length < 4) return Result.failure(Exception("La contraseña debe tener al menos 4 caracteres"))

        // Intentar registro en backend
        if (api != null) {
            try {
                val remoteUser = api.register(AuthRequestDto(username.trim(), password))
                // Guardar en Room como caché local
                val hash = hashPassword(password)
                val existing = userDao.findByUsername(username.trim())
                if (existing == null) {
                    userDao.insert(UserProfileEntity(id = remoteUser.id, username = remoteUser.username, passwordHash = hash))
                }
                return Result.success(remoteUser.id)
            } catch (e: retrofit2.HttpException) {
                val msg = if (e.code() == 409) "El nombre de usuario ya existe" else "Error del servidor: ${e.code()}"
                return Result.failure(Exception(msg))
            } catch (_: Exception) {
                // Sin conexión, registro solo local
            }
        }

        // Fallback: registro solo en Room
        val existing = userDao.findByUsername(username.trim())
        if (existing != null) return Result.failure(Exception("El nombre de usuario ya existe"))
        val hash = hashPassword(password)
        val id = userDao.insert(UserProfileEntity(username = username.trim(), passwordHash = hash))
        return Result.success(id)
    }

    suspend fun login(username: String, password: String): Result<UserProfileEntity> {
        // Intentar login en backend
        if (api != null) {
            try {
                val remoteUser = api.login(AuthRequestDto(username.trim(), password))
                // Sincronizar en Room
                val hash = hashPassword(password)
                val local = userDao.findByUsername(remoteUser.username)
                if (local == null) {
                    userDao.insert(UserProfileEntity(id = remoteUser.id, username = remoteUser.username, passwordHash = hash))
                }
                val saved = userDao.getById(remoteUser.id) ?: userDao.findByUsername(remoteUser.username)
                if (saved != null) return Result.success(saved)
            } catch (e: retrofit2.HttpException) {
                return Result.failure(Exception("Usuario o contraseña incorrectos"))
            } catch (_: Exception) {
                // Sin conexión, login local
            }
        }

        // Fallback: login solo local con Room
        val hash = hashPassword(password)
        val user = userDao.login(username.trim(), hash)
            ?: return Result.failure(Exception("Usuario o contraseña incorrectos"))
        return Result.success(user)
    }

    suspend fun getUserById(id: Long): UserProfileEntity? = userDao.getById(id)

    // ── Personajes ──────────────────────────────────────────────────────────

    fun observeCharacters(userId: Long): Flow<List<UserCharacterEntity>> =
        characterDao.observeByUser(userId)

    suspend fun addCharacter(userId: Long, name: String, raceName: String, className: String, level: Int): Long {
        return characterDao.insert(
            UserCharacterEntity(
                userId = userId,
                name = name.trim(),
                raceName = raceName,
                className = className,
                level = level.coerceIn(1, 60)
            )
        )
    }

    suspend fun updateCharacter(character: UserCharacterEntity) = characterDao.update(character)

    suspend fun deleteCharacter(id: Long) = characterDao.deleteById(id)

    // ── Progreso de mazmorras ─────────────────────────────────────────────

    fun observeDungeonProgress(userId: Long): Flow<List<DungeonProgressEntity>> =
        dungeonProgressDao.observeByUser(userId)

    suspend fun toggleDungeonCompletion(userId: Long, zoneId: Long, zoneName: String) {
        val existing = dungeonProgressDao.getByUserAndZone(userId, zoneId)
        if (existing == null) {
            dungeonProgressDao.upsert(
                DungeonProgressEntity(
                    userId = userId, zoneId = zoneId, zoneName = zoneName,
                    completed = true, completedAt = System.currentTimeMillis()
                )
            )
        } else {
            val newCompleted = !existing.completed
            dungeonProgressDao.updateCompletion(
                userId, zoneId, newCompleted,
                if (newCompleted) System.currentTimeMillis() else null
            )
        }
    }

    suspend fun getCompletedCount(userId: Long): Int =
        dungeonProgressDao.countCompleted(userId)

    suspend fun isDungeonCompleted(userId: Long, zoneId: Long): Boolean {
        val entry = dungeonProgressDao.getByUserAndZone(userId, zoneId)
        return entry?.completed == true
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}

