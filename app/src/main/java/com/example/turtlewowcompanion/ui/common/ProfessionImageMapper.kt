package com.example.turtlewowcompanion.ui.common

import com.example.turtlewowcompanion.R

/**
 * Devuelve el recurso drawable del icono de cada profesión.
 * Devuelve null si no hay imagen disponible (se usará un fallback).
 */
fun professionImageRes(professionName: String): Int? {
    val key = professionName.lowercase().trim()
    return when {
        key.contains("alchemy") || key.contains("alquimia")         -> optionalProfRes("img_prof_alchemy")
        key.contains("blacksmithing") || key.contains("herrería")   -> optionalProfRes("img_prof_blacksmithing")
        key.contains("enchanting") || key.contains("encantamiento") -> optionalProfRes("img_prof_enchanting")
        key.contains("engineering") || key.contains("ingeniería")   -> optionalProfRes("img_prof_engineering")
        key.contains("herbalism") || key.contains("herboristería")  -> optionalProfRes("img_prof_herbalism")
        key.contains("leatherworking") || key.contains("peletería") -> optionalProfRes("img_prof_leatherworking")
        key.contains("mining") || key.contains("minería")           -> optionalProfRes("img_prof_mining")
        key.contains("skinning") || key.contains("desuello")        -> optionalProfRes("img_prof_skinning")
        key.contains("tailoring") || key.contains("sastrería")      -> optionalProfRes("img_prof_tailoring")
        key.contains("cooking") || key.contains("cocina")           -> optionalProfRes("img_prof_cooking")
        key.contains("first aid") || key.contains("primeros")       -> optionalProfRes("img_prof_firstaid")
        key.contains("fishing") || key.contains("pesca")            -> optionalProfRes("img_prof_fishing")
        else                                                         -> null
    }
}

private fun optionalProfRes(name: String): Int? {
    return try {
        val field = R.drawable::class.java.getDeclaredField(name)
        field.getInt(null)
    } catch (_: Exception) {
        null
    }
}

