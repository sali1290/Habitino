package com.sali.habitino.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sali.habitino.view.screen.HabitScreen
import com.sali.habitino.view.screen.MainHabitScreen
import com.sali.habitino.view.theme.HabitinoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitinoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SharedTransitionLayout {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = "MainHabitScreen"
                        ) {

                            composable("MainHabitScreen") {
                                MainHabitScreen(animatedVisibilityScope = this) { title, description, state, isDone ->
                                    navController.navigate("HabitScreen/$title/$description/$state/$isDone")
                                }
                            }

                            composable(
                                route = "HabitScreen/{title}/{description}/{state}/{isDone}",
                                arguments = listOf(
                                    navArgument(name = "title") { type = NavType.StringType },
                                    navArgument(name = "description") { type = NavType.StringType },
                                    navArgument(name = "state") { type = NavType.StringType },
                                    navArgument(name = "isDone") { type = NavType.BoolType }
                                )
                            ) {
                                val title = it.arguments?.getString("title") ?: ""
                                val description = it.arguments?.getString("description") ?: ""
                                val state = it.arguments?.getString("state") ?: ""
                                val isDone = it.arguments?.getBoolean("isDone") ?: true
                                HabitScreen(
                                    animatedVisibilityScope = this,
                                    title = title,
                                    description = description,
                                    state = state,
                                    isDone = isDone
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}