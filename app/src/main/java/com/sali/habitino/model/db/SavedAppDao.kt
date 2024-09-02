package com.sali.habitino.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.sali.habitino.model.dto.SavedApp

@Dao
interface SavedAppDao {

    @Query("SELECT * FROM SavedApp")
    fun getAll(): List<SavedApp>

    @Upsert
    fun upsertApp(app: SavedApp)

    @Delete
    fun deleteApp(app: SavedApp)
}