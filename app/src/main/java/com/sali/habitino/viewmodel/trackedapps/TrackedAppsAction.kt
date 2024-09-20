package com.sali.habitino.viewmodel.trackedapps

import com.sali.habitino.model.dto.SavedApp

sealed interface TrackedAppsAction {

    data object GetSavedApps : TrackedAppsAction

    data class AddApp(val savedApp: SavedApp) : TrackedAppsAction

    data class RemoveApp(val savedApp: SavedApp) : TrackedAppsAction

}