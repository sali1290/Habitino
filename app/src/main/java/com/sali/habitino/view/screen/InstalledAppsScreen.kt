package com.sali.habitino.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sali.habitino.R
import com.sali.habitino.model.dto.SavedApp
import com.sali.habitino.view.component.AppItem
import com.sali.habitino.view.component.AppItemSeparator
import com.sali.habitino.view.component.SaveMessageDialog
import com.sali.habitino.viewmodel.installedapps.InstalledAppsAction
import com.sali.habitino.viewmodel.installedapps.InstalledAppsViewModel


@Composable
fun InstalledAppScreen(installedAppsViewModel: InstalledAppsViewModel = hiltViewModel()) {
    val savedApps = remember { mutableStateListOf<String>() }
    val installedAppsState by installedAppsViewModel.installedAppsState.collectAsStateWithLifecycle()

    var savedApp by remember { mutableStateOf<SavedApp?>(null) }
    var showSaveMessage by remember { mutableStateOf(false) }
    if (showSaveMessage) {
        SaveMessageDialog(
            onCancel = { showSaveMessage = false },
            onConfirm = { message ->
                savedApp?.message = message
                savedApp?.let {
                    savedApps.add(it.packageName)
                    installedAppsViewModel.onAction(InstalledAppsAction.AddApp(it))
                }
                showSaveMessage = false
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        installedAppsViewModel.onAction(InstalledAppsAction.GetAllInstalledApps)
        installedAppsViewModel.onAction(InstalledAppsAction.GetSavedApps)
    }

    LaunchedEffect(installedAppsState.savedApps) {
        if (installedAppsState.savedApps.isNotEmpty()) {
            installedAppsState.savedApps.forEach {
                savedApps.add(it.packageName)
            }
        }
    }

    if (installedAppsState.installedApps.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            contentWindowInsets = WindowInsets(
                left = 10.dp,
                top = 0.dp,
                right = 10.dp,
                bottom = 0.dp
            )
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                Text(
                    text = stringResource(R.string.installed_apps),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn {

                    items(installedAppsState.installedApps) {
                        AppItem(
                            icon = it.appIcon,
                            name = it.name,
                            initialCheck = savedApps.contains(it.packageName)
                        ) { isChecked ->
                            if (isChecked) {
                                showSaveMessage = true
                                savedApp = SavedApp(
                                    id = 0,
                                    name = it.name,
                                    appIcon = it.appIcon,
                                    packageName = it.packageName,
                                    message = ""
                                )
                            } else {
                                installedAppsState.savedApps.forEach { savedApp ->
                                    if (it.packageName == savedApp.packageName)
                                        installedAppsViewModel.onAction(
                                            InstalledAppsAction.RemoveApp(
                                                savedApp
                                            )
                                        )
                                }
                                savedApp = null
                                savedApps.remove(it.packageName)
                            }
                        }
                        AppItemSeparator()
                    }
                }
            }
        }
    }

}