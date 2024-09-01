package com.sali.habitino.model.repo

import android.content.Context
import com.sali.habitino.model.db.AppTrackDao
import com.sali.habitino.model.dto.AppModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppTrackRepoImpl @Inject constructor(
    private val appTrackDao: AppTrackDao,
    @ApplicationContext private val context: Context
) : AppTrackRepo {

    private val allInstalledApps: MutableList<AppModel> = mutableListOf()
    override suspend fun getAllInstalledApps(): List<AppModel> {
        val savedApps = mutableListOf<String>()
        getAllSavedApps().forEach { savedApps.add(it.packageName) }
        context.packageManager.getInstalledApplications(0).forEach {
            val appModel = AppModel(
                id = 0,
                name = it.loadLabel(context.packageManager).toString(),
                appIcon = it.loadIcon(context.packageManager),
                status = 0,
                packageName = it.packageName
            )
            if (savedApps.contains(it.packageName)) appModel.status = 1
            allInstalledApps.add(appModel)
        }
        return allInstalledApps
    }

    override suspend fun getAllSavedApps(): List<AppModel> {
        return appTrackDao.getAll()
    }

    override suspend fun addApp(appModel: AppModel) {
        appTrackDao.upsertApp(appModel)
    }

    override suspend fun removeApp(appModel: AppModel) {
        appTrackDao.deleteApp(appModel)
    }
}