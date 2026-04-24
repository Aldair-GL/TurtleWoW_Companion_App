package com.example.turtlewowcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.turtlewowcompanion.di.AppContainer
import com.example.turtlewowcompanion.ui.navigation.BottomNavBar
import com.example.turtlewowcompanion.ui.navigation.NavGraph
import com.example.turtlewowcompanion.ui.navigation.Screen
import com.example.turtlewowcompanion.ui.navigation.bottomNavItems
import com.example.turtlewowcompanion.ui.screens.auth.AuthScreen
import com.example.turtlewowcompanion.ui.screens.auth.AuthViewModel
import com.example.turtlewowcompanion.ui.theme.DarkBackground
import com.example.turtlewowcompanion.ui.theme.TurtleWoWCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val container = (application as TurtleWoWApplication).container

        setContent {
            TurtleWoWCompanionTheme(darkTheme = true) {
                AppRoot(container = container)
            }
        }
    }
}

@Composable
private fun AppRoot(container: AppContainer) {
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory(container.userRepository))
    val authState by authViewModel.state.collectAsState()

    if (authState.loggedInUserId == null) {
        AuthScreen(
            viewModel = authViewModel,
            onAuthenticated = { _, _ -> }
        )
    } else {
        MainApp(
            container = container,
            userId = authState.loggedInUserId!!,
            username = authState.loggedInUsername ?: "",
            onLogout = { authViewModel.logout() }
        )
    }
}

@Composable
private fun MainApp(
    container: AppContainer,
    userId: Long,
    username: String,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavRoutes = bottomNavItems.map { it.route }
    val showBottomBar = currentRoute in bottomNavRoutes && currentRoute != Screen.Splash.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground,
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            container = container,
            userId = userId,
            username = username,
            onLogout = onLogout,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
