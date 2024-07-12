package com.sali.habitino.model.repo

import com.sali.habitino.model.dto.Habit

interface RemoteHabitRepo {

    suspend fun getAllHabits(): List<Habit>

    suspend fun updateHabit(habit: Habit)

}