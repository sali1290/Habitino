package com.sali.habitino.model.repo

import com.sali.habitino.model.dto.AppModel

interface AppTrackRepo {

    suspend fun getAllInstalledApps(): List<AppModel>

//    suspend fun getAllSavedApps(): List<AppModel>
//
//    suspend fun updateAppState(appModel: AppModel)
//
//    suspend fun deleteApp(appModel: AppModel)

}