package com.sali.habitino.viewmodel.apptrack

import com.sali.habitino.model.dto.AppModel

data class TrackingAppsState(
    val loading: Boolean = false,
    val error: String? = null,
    val savedApps: List<AppModel> = emptyList(),
)