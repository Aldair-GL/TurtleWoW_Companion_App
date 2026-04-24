package com.example.turtlewowcompanion.ui.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.turtlewowcompanion.ui.theme.GlassSurface
import com.example.turtlewowcompanion.ui.theme.WowGold

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Inicio", Icons.Default.Home, Screen.Home.route),
    BottomNavItem("Buscar", Icons.Default.Search, Screen.Search.route),
    BottomNavItem("Favoritos", Icons.Default.Favorite, Screen.Favorites.route),
    BottomNavItem("Perfil", Icons.Default.AccountCircle, Screen.Profile.route),
    BottomNavItem("Ajustes", Icons.Default.Settings, Screen.Settings.route)
)

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = GlassSurface.copy(alpha = 0.85f),
        contentColor = WowGold,
        tonalElevation = 0.dp
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            val iconColor by animateColorAsState(
                targetValue = if (isSelected) WowGold else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(300),
                label = "navIconColor"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) WowGold else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(300),
                label = "navTextColor"
            )

            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = iconColor,
                        modifier = Modifier.size(if (isSelected) 26.dp else 22.dp)
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = textColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = WowGold,
                    selectedTextColor = WowGold,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = WowGold.copy(alpha = 0.15f)
                )
            )
        }
    }
}
