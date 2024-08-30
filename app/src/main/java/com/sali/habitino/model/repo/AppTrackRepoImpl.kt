package com.sali.habitino.model.repo

import android.content.Context
import com.sali.habitino.model.dto.AppModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppTrackRepoImpl @Inject constructor(
//    private val appTrackDao: AppTrackDao,
    @ApplicationContext private val context: Context
) : AppTrackRepo {

    private val allInstalledApps: MutableList<AppModel> = mutableListOf()
    override suspend fun getAllInstalledApps(): List<AppModel> {
        if (allInstalledApps.isNotEmpty())
            return allInstalledApps

        context.packageManager.getInstalledApplications(0).forEach {
            allInstalledApps.add(
                AppModel(
                    id = 0,
                    name = it.loadLabel(context.packageManager).toString(),
                    appIcon = it.loadIcon(context.packageManager),
                    packageName = it.packageName
                )
            )
        }
        return allInstalledApps
    }

//    override suspend fun getAllSavedApps(): List<AppModel> {
//        return appTrackDao.getAll()
//    }
//
//    override suspend fun updateAppState(appModel: AppModel) {
//        appTrackDao.upsert(appModel)
//    }
//
//    override suspend fun deleteApp(appModel: AppModel) {
//        appTrackDao.delete(appModel)
//    }
}