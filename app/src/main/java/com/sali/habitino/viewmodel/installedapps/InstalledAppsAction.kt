package com.sali.habitino.viewmodel.installedapps

import com.sali.habitino.model.dto.AppModel

sealed interface InstalledAppsAction {

    data object GetAllInstalledApps : InstalledAppsAction

    data object GetSavedApps : InstalledAppsAction

    data class AddApp(val appModel: AppModel) : InstalledAppsAction

    data class RemoveApp(val appModel: AppModel) : InstalledAppsAction

}