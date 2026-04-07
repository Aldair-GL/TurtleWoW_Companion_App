package com.example.turtlewowcompanion.ui.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.turtlewowcompanion.ui.theme.WowBronze

@Composable
fun WowDivider(
    modifier: Modifier = Modifier,
    color: Color = WowBronze
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .padding(horizontal = 24.dp)
    ) {
        val centerY = size.height / 2
        val centerX = size.width / 2

        // Línea izquierda con desvanecimiento
        drawLine(
            color = color.copy(alpha = 0.1f),
            start = Offset(0f, centerY),
            end = Offset(centerX - 20f, centerY),
            strokeWidth = 1f
        )
        drawLine(
            color = color.copy(alpha = 0.6f),
            start = Offset(centerX - 80f, centerY),
            end = Offset(centerX - 20f, centerY),
            strokeWidth = 1f
        )

        // Diamante central
        val diamondSize = 5f
        drawCircle(
            color = color,
            radius = diamondSize,
            center = Offset(centerX, centerY)
        )

        // Línea derecha con desvanecimiento
        drawLine(
            color = color.copy(alpha = 0.6f),
            start = Offset(centerX + 20f, centerY),
            end = Offset(centerX + 80f, centerY),
            strokeWidth = 1f
        )
        drawLine(
            color = color.copy(alpha = 0.1f),
            start = Offset(centerX + 20f, centerY),
            end = Offset(size.width, centerY),
            strokeWidth = 1f
        )
    }
}

