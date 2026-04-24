package com.example.turtlewowcompanion.data.repository

import com.example.turtlewowcompanion.data.local.dao.DungeonProgressDao
import com.example.turtlewowcompanion.data.local.dao.UserCharacterDao
import com.example.turtlewowcompanion.data.local.dao.UserProfileDao
import com.example.turtlewowcompanion.data.local.entity.DungeonProgressEntity
import com.example.turtlewowcompanion.data.local.entity.UserCharacterEntity
import com.example.turtlewowcompanion.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow
import java.security.MessageDigest

class UserRepository(
    private val userDao: UserProfileDao,
    private val characterDao: UserCharacterDao,
    private val dungeonProgressDao: DungeonProgressDao
) {
    // ── Autenticación ──────────────────────────────────────────────────────

    suspend fun register(username: String, password: String): Result<Long> {
        val existing = userDao.findByUsername(username.trim())
        if (existing != null) return Result.failure(Exception("El nombre de usuario ya existe"))
        if (username.trim().length < 3) return Result.failure(Exception("El nombre debe tener al menos 3 caracteres"))
        if (password.length < 4) return Result.failure(Exception("La contraseña debe tener al menos 4 caracteres"))
        val hash = hashPassword(password)
        val id = userDao.insert(UserProfileEntity(username = username.trim(), passwordHash = hash))
        return Result.success(id)
    }

    suspend fun login(username: String, password: String): Result<UserProfileEntity> {
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
                    userId = userId,
                    zoneId = zoneId,
                    zoneName = zoneName,
                    completed = true,
                    completedAt = System.currentTimeMillis()
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

    // ── Utilidades ──────────────────────────────────────────────────────────

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}

