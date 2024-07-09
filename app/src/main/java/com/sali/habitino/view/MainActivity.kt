package com.sali.habitino.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sali.habitino.view.screen.HabitScreen
import com.sali.habitino.view.screen.MainHabitScreen
import com.sali.habitino.view.screen.Screens
import com.sali.habitino.view.theme.HabitinoTheme
import com.sali.habitino.view.utils.ArgumentKeys
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HabitinoNavHost(modifier: Modifier) {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screens.MainHabitScreen.route,
            modifier = modifier
        ) {

            composable(Screens.MainHabitScreen.route) {
                MainHabitScreen(animatedVisibilityScope = this) { title, description, state, isDone ->
                    navController.navigate("HabitScreen/$title/$description/$state/$isDone")
                }
            }

            composable(
                route = Screens.HabitScreen.route,
                arguments = listOf(
                    navArgument(name = ArgumentKeys.TITLE) { type = NavType.StringType },
                    navArgument(name = ArgumentKeys.DESCRIPTION) { type = NavType.StringType },
                    navArgument(name = ArgumentKeys.STATE) { type = NavType.StringType },
                    navArgument(name = ArgumentKeys.IS_DONE) { type = NavType.BoolType }
                )
            ) {
                val title = it.arguments?.getString(ArgumentKeys.TITLE) ?: " "
                val description = it.arguments?.getString(ArgumentKeys.DESCRIPTION) ?: " "
                val state = it.arguments?.getString(ArgumentKeys.STATE) ?: " "
                val isDone = it.arguments?.getBoolean(ArgumentKeys.IS_DONE) ?: true
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