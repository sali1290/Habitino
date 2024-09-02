package com.sali.habitino.viewmodel.installedapps

import com.sali.habitino.model.dto.InstalledApp
import com.sali.habitino.model.dto.SavedApp

data class InstalledAppsState(
    val loading: Boolean = false,
    val error: String? = null,
    val installedApps: List<InstalledApp> = emptyList(),
    val savedApps: List<SavedApp> = emptyList(),
)