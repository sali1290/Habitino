package com.sali.habitino.model.repo

import com.sali.habitino.model.db.AppTrackDao
import com.sali.habitino.model.dto.AppModel
import javax.inject.Inject

class AppTrackRepoImpl @Inject constructor(private val appTrackDao: AppTrackDao) : AppTrackRepo {
    override suspend fun getAllApps(): List<AppModel> {
        return appTrackDao.getAll()
    }

    override suspend fun updateAppState(appModel: AppModel) {
        appTrackDao.upsert(appModel)
    }

    override suspend fun deleteApp(appModel: AppModel) {
        appTrackDao.delete(appModel)
    }
}