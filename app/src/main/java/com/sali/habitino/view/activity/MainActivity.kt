package com.sali.habitino.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sali.habitino.R
import com.sali.habitino.view.component.PermissionDialog
import com.sali.habitino.view.screen.InstalledAppScreen
import com.sali.habitino.view.screen.Screens
import com.sali.habitino.view.screen.TrackedAppsScreen
import com.sali.habitino.view.theme.HabitinoTheme
import com.sali.habitino.view.utile.checkAppPermissions
import com.sali.habitino.view.utile.grantAppRequiredPermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    override fun onResume() {
        super.onResume()
        val permissionsGranted = this.checkAppPermissions()
        setContent {
            HabitinoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val showPermissionsDialog = remember { mutableStateOf(true) }
                    if (permissionsGranted)
                        HabitinoNavHost(modifier = Modifier.padding(innerPadding))
                    else
                        PermissionDialog(
                            isShown = showPermissionsDialog,
                            title = stringResource(R.string.granting_permissions),
                            onRejectClick = { this.finish() },
                            onAcceptClick = { this.grantAppRequiredPermissions() })
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
//        composable(Screens.MainHabitScreen.route) {
//            MainHabitScreen()
//        }
        composable(Screens.TrackedAppsScreen.route) {
            TrackedAppsScreen(navController)
        }
        composable(Screens.InstalledAppsScreen.route) {
            InstalledAppScreen()
        }
    }
}

