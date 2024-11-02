package com.sali.habitino.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sali.habitino.view.screen.InstalledAppScreen
import com.sali.habitino.view.screen.Screens
import com.sali.habitino.view.screen.TrackedAppsScreen
import com.sali.habitino.view.theme.HabitinoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitinoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HabitinoNavHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HabitinoNavHost(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.TrackedAppsScreen.route,
        modifier = modifier
    ) {
        composable(Screens.TrackedAppsScreen.route) {
            TrackedAppsScreen(navController)
        }
        composable(Screens.InstalledAppsScreen.route) {
            InstalledAppScreen()
        }
    }
}

