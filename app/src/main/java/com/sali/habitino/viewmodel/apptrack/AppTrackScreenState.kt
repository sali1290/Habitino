package com.sali.habitino.viewmodel.apptrack

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sali.habitino.model.dto.AppModel

data class AppTrackScreenState(
    val allApps: SnapshotStateList<AppModel>,
    val savedApps: SnapshotStateList<AppModel>,
    val loading: Boolean = false,
)