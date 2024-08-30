package com.sali.habitino.viewmodel.installedapps

import com.sali.habitino.model.dto.AppModel

data class InstalledAppsState(
    val loading: Boolean = false,
    val error: String? = null,
    val installedApps: List<AppModel> = emptyList(),
    val savedApps: List<AppModel> = emptyList(),
)