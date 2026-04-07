package com.example.turtlewowcompanion.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.turtlewowcompanion.ui.theme.factionColorsFor

@Composable
fun FactionBadge(
    faction: String,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true
) {
    val colors = factionColorsFor(faction)
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = colors.primary.copy(alpha = 0.2f),
        contentColor = colors.accent
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(colors.iconRes),
                contentDescription = faction,
                tint = colors.accent,
                modifier = Modifier.size(16.dp)
            )
            if (showLabel) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = faction,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.accent
                )
            }
        }
    }
}

