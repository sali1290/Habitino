package com.sali.habitino.model.repo

import com.sali.habitino.model.dto.SelfAddedHabit

interface SelfAddedHabitRepo {

    suspend fun insert(habit: SelfAddedHabit)

    suspend fun delete(habit: SelfAddedHabit)

    suspend fun getAllHabits(): List<SelfAddedHabit>

    suspend fun getSelfAddedHabit(title: String): SelfAddedHabit

    suspend fun updateHabit(habit: SelfAddedHabit)
}