package com.sali.habitino.model.repo

import android.content.Context
import com.sali.habitino.model.db.SavedAppDao
import com.sali.habitino.model.dto.InstalledApp
import com.sali.habitino.model.dto.SavedApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppsRepoImpl @Inject constructor(
    private val savedAppDao: SavedAppDao,
    @ApplicationContext private val context: Context
) : AppsRepo {

    private val allInstalledApps: MutableList<InstalledApp> = mutableListOf()
    override suspend fun getAllInstalledApps(): List<InstalledApp> {
        val savedApps = mutableListOf<String>()
        getAllSavedApps().forEach { savedApps.add(it.packageName) }
        context.packageManager.getInstalledApplications(0).forEach {
            val installedApp = if (savedApps.contains(it.packageName)) {
                InstalledApp(
                    name = it.loadLabel(context.packageManager).toString(),
                    appIcon = it.loadIcon(context.packageManager),
                    status = 1,
                    packageName = it.packageName
                )
            } else {
                InstalledApp(
                    name = it.loadLabel(context.packageManager).toString(),
                    appIcon = it.loadIcon(context.packageManager),
                    status = 0,
                    packageName = it.packageName
                )
            }
            allInstalledApps.add(installedApp)
        }
        return allInstalledApps
    }

    override suspend fun getAllSavedApps(): List<SavedApp> {
        return savedAppDao.getAll()
    }

    override suspend fun addApp(savedApp: SavedApp) {
        savedAppDao.upsertApp(savedApp)
    }

    override suspend fun removeApp(savedApp: SavedApp) {
        savedAppDao.deleteApp(savedApp)
    }
}