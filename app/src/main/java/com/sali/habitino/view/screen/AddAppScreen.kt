package com.sali.habitino.view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sali.habitino.view.component.AppItem


@Composable
fun AddAppScreen() {
    val savedApps = remember { mutableStateListOf<AppModel>() }
    val appList = remember { mutableStateListOf<AppModel>() }
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        context.packageManager.getInstalledApplications(0).forEach {
            appList.add(
                AppModel(
                    name = it.loadLabel(context.packageManager).toString(),
                    appIcon = it.loadIcon(context.packageManager),
                    status = 0,
                    packageName = it.packageName
                )
            )
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(appList) {
            AppItem(
                icon = it.appIcon,
                name = it.name,
                initialCheck = savedApps.contains(it)
            ) { isChecked ->
                if (isChecked)
                    savedApps.add(it)
                else
                    savedApps.remove(it)
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