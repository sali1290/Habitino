package com.sali.habitino.viewmodel.apptrack

import com.sali.habitino.model.dto.AppModel

sealed interface AppTrackAction {

    data object GetSavedApps : AppTrackAction

    data class AddApp(val appModel: AppModel) : AppTrackAction

    data class RemoveApp(val appModel: AppModel) : AppTrackAction

}