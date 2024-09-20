package com.sali.habitino.viewmodel.trackedapps

import com.sali.habitino.model.dto.SavedApp

data class TrackedAppsState(
    val loading: Boolean = false,
    val error: String? = null,
    val savedApps: List<SavedApp> = emptyList(),
)