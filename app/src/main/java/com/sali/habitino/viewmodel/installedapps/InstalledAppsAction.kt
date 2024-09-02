package com.sali.habitino.viewmodel.installedapps

import com.sali.habitino.model.dto.SavedApp

sealed interface InstalledAppsAction {

    data object GetAllInstalledApps : InstalledAppsAction

    data object GetSavedApps : InstalledAppsAction

    data class AddApp(val savedApp: SavedApp) : InstalledAppsAction

    data class RemoveApp(val savedApp: SavedApp) : InstalledAppsAction

}