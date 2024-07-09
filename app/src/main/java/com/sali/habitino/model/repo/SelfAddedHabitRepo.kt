package com.sali.habitino.model.repo

import com.sali.habitino.model.dto.SelfAddedHabit

interface SelfAddedHabitRepo {

    suspend fun insert(selfAddedHabit: SelfAddedHabit)

    suspend fun delete(selfAddedHabit: SelfAddedHabit)

    suspend fun getAllHabits(): List<SelfAddedHabit>

    suspend fun getSelfAddedHabit(title: String): SelfAddedHabit

}