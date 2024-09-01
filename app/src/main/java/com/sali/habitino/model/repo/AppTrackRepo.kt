package com.sali.habitino.model.repo

import com.sali.habitino.model.dto.AppModel

interface AppTrackRepo {

    suspend fun getAllInstalledApps(): List<AppModel>

    suspend fun getAllSavedApps(): List<AppModel>

    suspend fun addApp(appModel: AppModel)

    suspend fun removeApp(appModel: AppModel)

}