package com.sali.habitino.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.sali.habitino.model.dto.AppModel

@Dao
interface AppTrackDao {

    @Query("SELECT * FROM AppModel")
    fun getAll(): List<AppModel>

    @Upsert
    fun upsertApp(app: AppModel)

    @Delete
    fun deleteApp(app: AppModel)
}