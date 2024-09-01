package com.sali.habitino.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sali.habitino.model.dto.AppModel
import com.sali.habitino.view.component.AppItem
import com.sali.habitino.viewmodel.installedapps.InstalledAppsAction
import com.sali.habitino.viewmodel.installedapps.InstalledAppsViewModel


@Composable
fun InstalledAppScreen(installedAppsViewModel: InstalledAppsViewModel = hiltViewModel()) {
    val savedApps = remember { mutableStateListOf<AppModel>() }
    val installedAppsState by installedAppsViewModel.installedAppsState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        installedAppsViewModel.onAction(InstalledAppsAction.GetAllInstalledApps)
        installedAppsViewModel.onAction(InstalledAppsAction.GetSavedApps)
    }

    LaunchedEffect(installedAppsState.savedApps) {
        if (installedAppsState.savedApps.isNotEmpty()) {
            savedApps.addAll(installedAppsState.savedApps)
        }
    }

    if (installedAppsState.installedApps.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(installedAppsState.installedApps) {
                AppItem(
                    icon = it.appIcon,
                    name = it.name,
                    initialCheck = it.status == 1
                ) { isChecked ->
                    if (isChecked) {
                        installedAppsViewModel.onAction(InstalledAppsAction.AddApp(it))
                        it.status = 1
                        savedApps.add(it)
                    } else {
                        savedApps.forEach { savedApp ->
                            if (it.packageName == savedApp.packageName)
                                installedAppsViewModel.onAction(
                                    InstalledAppsAction.RemoveApp(
                                        savedApp
                                    )
                                )
                        }
                        it.status = 0
                        savedApps.remove(it)
                    }
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