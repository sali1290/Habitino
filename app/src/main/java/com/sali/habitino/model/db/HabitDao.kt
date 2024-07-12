package com.sali.habitino.model.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sali.habitino.model.dto.Habit

@Dao
interface HabitDao {

    @Query("SELECT * FROM Habit")
    fun getAll(): List<Habit>

    @Upsert
    fun upsert(habit: Habit)

}