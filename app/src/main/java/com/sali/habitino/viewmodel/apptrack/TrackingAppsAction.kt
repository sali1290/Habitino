package com.sali.habitino.viewmodel.apptrack

import com.sali.habitino.model.dto.SavedApp

sealed interface TrackingAppsAction {

    data object GetSavedApps : TrackingAppsAction

    data class AddApp(val savedApp: SavedApp) : TrackingAppsAction

    data class RemoveApp(val savedApp: SavedApp) : TrackingAppsAction

}