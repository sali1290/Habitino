package com.sali.habitino.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sali.habitino.model.dto.SelfAddedHabit

@Database(entities = [SelfAddedHabit::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun selfAddedHabitDao(): SelfAddedHabitDao

}