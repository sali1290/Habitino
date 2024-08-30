package com.sali.habitino.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sali.habitino.view.screen.AddAppScreen
import com.sali.habitino.view.screen.AppTrackScreen
import com.sali.habitino.view.screen.MainHabitScreen
import com.sali.habitino.view.screen.Screens
import com.sali.habitino.view.theme.HabitinoTheme
import com.sali.habitino.viewmodel.HabitStatesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val habitStateViewMode: HabitStatesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        habitStateViewMode.checkAllHabitsState()
        setContent {
            HabitinoTheme {
//                        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
//                        startActivity(intent)
//                        val intent = Intent(
//                            Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse(
//                                "package:$packageName"
//                            )
//                        )
//                AllApplicationsScreen()
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
        startDestination = Screens.AppTrackScreen.route,
        modifier = modifier
    ) {
        composable(Screens.MainHabitScreen.route) {
            MainHabitScreen()
        }
        composable(Screens.AppTrackScreen.route) {
            AppTrackScreen(navController)
        }
        composable(Screens.AddAppScreen.route) {
            AddAppScreen()
        }
    }
}