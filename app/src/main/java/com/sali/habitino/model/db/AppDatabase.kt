package com.sali.habitino.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sali.habitino.model.dto.CommonHabit
import com.sali.habitino.model.dto.SavedApp
import com.sali.habitino.model.dto.SelfAddedHabit
import com.sali.habitino.model.utile.DrawableConverter
import com.sali.habitino.model.utile.TagConverter
import com.sali.habitino.model.utile.TimeConverters

@Database(entities = [SelfAddedHabit::class, CommonHabit::class, SavedApp::class], version = 1)
@TypeConverters(TimeConverters::class, TagConverter::class, DrawableConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun selfAddedHabitDao(): SelfAddedHabitDao

    abstract fun habitDao(): HabitDao

    abstract fun savedAppDao(): SavedAppDao

}