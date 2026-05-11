package com.example.turtlewowcompanion.ui.common

import com.example.turtlewowcompanion.R

/**
 * Devuelve el recurso drawable del retrato de cada raza jugable.
 */
fun racePortraitRes(raceName: String): Int? {
    return when (raceName.lowercase().trim()) {
        "human", "humano", "humana"       -> R.drawable.img_race_human
        "dwarf", "enano", "enana"         -> R.drawable.img_race_dwarf
        "night elf", "elfo de la noche", "elfa de la noche" -> R.drawable.img_race_nightelf
        "gnome", "gnomo", "gnoma"         -> R.drawable.img_race_gnome
        "orc", "orco", "orca"             -> R.drawable.img_race_orc
        "undead", "no-muerto", "no muerto", "undead (forsaken)", "renegado", "renegada" -> R.drawable.img_race_undead
        "tauren"                          -> R.drawable.img_race_tauren
        "troll"                           -> R.drawable.img_race_troll
        else                              -> null
    }
}

