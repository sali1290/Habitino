package com.sali.habitino.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sali.habitino.model.dto.SavedApp
import com.sali.habitino.model.utile.DrawableConverter

@Database(entities = [SavedApp::class], version = 1)
@TypeConverters(DrawableConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun savedAppDao(): SavedAppDao

}