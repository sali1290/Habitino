package com.sali.habitino.viewmodel.installedapps

sealed interface InstalledAppsAction {

    data object GetAllInstalledApps : InstalledAppsAction

    data object GetSavedApps : InstalledAppsAction

}