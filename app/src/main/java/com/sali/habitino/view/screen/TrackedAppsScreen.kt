package com.sali.habitino.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sali.habitino.view.component.AppItem
import com.sali.habitino.viewmodel.apptrack.TrackingAppsAction
import com.sali.habitino.viewmodel.apptrack.TrackingAppsViewModel


@Composable
fun AppTrackScreen(
    navController: NavController,
    trackingAppsViewModel: TrackingAppsViewModel = hiltViewModel()
) {
    val trackingAppsState by trackingAppsViewModel.trackingAppsState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        trackingAppsViewModel.onAction(TrackingAppsAction.GetSavedApps)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.InstalledAppScreen.route) }) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Add new app")
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add app")
                }
            }
        },
        contentWindowInsets = WindowInsets(left = 10.dp, top = 0.dp, right = 10.dp, bottom = 0.dp)
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            item {
                Text(
                    text = "Tracked apps",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            itemsIndexed(trackingAppsState.savedApps) { _, item ->
                AppItem(
                    icon = item.appIcon,
                    name = item.name,
                    initialCheck = trackingAppsState.savedApps.contains(item)
                ) { isChecked ->
                    if (isChecked)
                        trackingAppsViewModel.onAction(TrackingAppsAction.AddApp(item))
                    else
                        trackingAppsViewModel.onAction(TrackingAppsAction.RemoveApp(item))
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                        .height(2.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
            }
        }
    }

}
