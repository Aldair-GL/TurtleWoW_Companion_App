package com.example.turtlewowcompanion.ui.common

import com.example.turtlewowcompanion.R

/**
 * Devuelve el recurso drawable del retrato de cada clase jugable.
 */
fun classPortraitRes(className: String): Int? {
    val key = className.lowercase().trim()
    return when {
        key.contains("warrior") || key.contains("guerrero")                -> R.drawable.img_class_warrior
        key.contains("paladin") || key.contains("palad")                   -> R.drawable.img_class_paladin
        key.contains("hunter") || key.contains("cazador")                  -> R.drawable.img_class_hunter
        key.contains("rogue") || key.contains("picaro") || key.contains("pícaro") -> R.drawable.img_class_rogue
        key.contains("priest") || key.contains("sacerdote")                -> R.drawable.img_class_priest
        key.contains("shaman") || key.contains("cham")                     -> R.drawable.img_class_shaman
        key.contains("mage") || key == "mago"                              -> R.drawable.img_class_mage
        key.contains("warlock") || key.contains("brujo")                   -> R.drawable.img_class_warlock
        key.contains("druid") || key.contains("druida")                    -> R.drawable.img_class_druid
        else                                                               -> null
    }
}


