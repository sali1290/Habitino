package com.sali.habitino.view.screen

import android.app.Activity
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sali.habitino.R
import com.sali.habitino.model.dto.SavedApp
import com.sali.habitino.view.component.AppItem
import com.sali.habitino.view.component.AppItemSeparator
import com.sali.habitino.view.component.PermissionDialog
import com.sali.habitino.view.component.SaveMessageDialog
import com.sali.habitino.view.util.checkAppPermissions
import com.sali.habitino.view.util.grantAppRequiredPermissions
import com.sali.habitino.viewmodel.trackedapps.TrackedAppsAction
import com.sali.habitino.viewmodel.trackedapps.TrackedAppsViewModel

@Composable
fun TrackedAppsScreen(navController: NavController) {
    CheckPermissions()
    TrackedApps(navController = navController)
}

@Composable
private fun CheckPermissions() {
    val context = LocalContext.current
    val activity = (context as? Activity)
    val permissionsGranted = context.checkAppPermissions()
    val showPermissionsDialog = remember { mutableStateOf(true) }
    if (!permissionsGranted)
        PermissionDialog(
            isShown = showPermissionsDialog,
            title = stringResource(R.string.granting_permissions),
            onRejectClick = { activity?.finish() },
            onAcceptClick = { context.grantAppRequiredPermissions() })
}

@Composable
private fun TrackedApps(
    navController: NavController,
    trackedAppsViewModel: TrackedAppsViewModel = hiltViewModel()
) {
    val trackingAppsState by trackedAppsViewModel.trackedAppsState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        trackedAppsViewModel.onAction(TrackedAppsAction.GetSavedApps)
    }

    var savedApp by remember { mutableStateOf<SavedApp?>(null) }
    var showSaveMessage by remember { mutableStateOf(false) }
    var initialMessage by remember { mutableStateOf("") }
    if (showSaveMessage) {
        SaveMessageDialog(
            initialMessage = initialMessage,
            onCancel = { showSaveMessage = false },
            onConfirm = { message ->
                savedApp?.message = message
                savedApp?.let {
                    trackedAppsViewModel.onAction(TrackedAppsAction.AddApp(it))
                }
                showSaveMessage = false
            }
        )
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screens.InstalledAppsScreen.route) }) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.add_new_app))
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
                    text = stringResource(R.string.tracked_apps),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            itemsIndexed(trackingAppsState.savedApps) { _, item ->
                AppItem(
                    icon = item.appIcon,
                    name = item.name,
                    message = item.message,
                    initialCheck = trackingAppsState.savedApps.contains(item),
                    onEditClick = {
                        showSaveMessage = true
                        initialMessage = item.message
                        savedApp = item
                    }
                ) { isChecked ->
                    if (isChecked)
                        trackedAppsViewModel.onAction(TrackedAppsAction.AddApp(item))
                    else
                        trackedAppsViewModel.onAction(TrackedAppsAction.RemoveApp(item))
                }
                AppItemSeparator()
            }
        }
    }
}
