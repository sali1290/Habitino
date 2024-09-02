package com.sali.habitino.viewmodel.apptrack

import com.sali.habitino.model.dto.AppModel

sealed interface TrackingAppsAction {

    data object GetSavedApps : TrackingAppsAction

    data class AddApp(val appModel: AppModel) : TrackingAppsAction

    data class RemoveApp(val appModel: AppModel) : TrackingAppsAction

}