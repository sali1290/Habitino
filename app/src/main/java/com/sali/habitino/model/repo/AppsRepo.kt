package com.sali.habitino.model.repo

import com.sali.habitino.model.dto.InstalledApp
import com.sali.habitino.model.dto.SavedApp

interface AppsRepo {

    suspend fun getAllInstalledApps(): List<InstalledApp>

    suspend fun getAllSavedApps(): List<SavedApp>

    suspend fun addApp(savedApp: SavedApp)

    suspend fun removeApp(savedApp: SavedApp)

}