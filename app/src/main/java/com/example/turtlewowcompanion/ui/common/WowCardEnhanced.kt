package com.example.turtlewowcompanion.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.turtlewowcompanion.ui.theme.GradientDark
import com.example.turtlewowcompanion.ui.theme.GradientTransparent
import com.example.turtlewowcompanion.ui.theme.factionColorsFor

@Composable
fun WowCardEnhanced(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    backgroundBrush: Brush? = null,
    imageRes: Int? = null,
    faction: String? = null,
    badge: String? = null,
    onClick: () -> Unit = {}
) {
    val factionColors = faction?.let { factionColorsFor(it) }
    val accentColor = factionColors?.primary

    GlassCard(
        modifier = modifier.fillMaxWidth().height(88.dp),
        accentColor = accentColor,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (backgroundBrush != null || imageRes != null) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                        .then(
                            if (backgroundBrush != null) Modifier.background(backgroundBrush)
                            else Modifier
                        )
                ) {
                    if (imageRes != null) {
                        Image(
                            painter = painterResource(imageRes),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(GradientTransparent, GradientDark),
                                    startX = 0f,
                                    endX = 200f
                                )
                            )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = accentColor ?: MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (badge != null) {
                FactionBadge(
                    faction = badge,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        }
    }
}
