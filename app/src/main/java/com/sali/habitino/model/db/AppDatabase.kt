package com.sali.habitino.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sali.habitino.model.dto.Habit
import com.sali.habitino.model.dto.SelfAddedHabit
import com.sali.habitino.model.utils.Converters

@Database(entities = [SelfAddedHabit::class, Habit::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun selfAddedHabitDao(): SelfAddedHabitDao

    abstract fun habitDao(): HabitDao

}