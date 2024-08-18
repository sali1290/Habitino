package com.sali.habitino.model.repo

import com.sali.habitino.model.dto.CommonHabit

interface CommonHabitRepo {

    suspend fun getAllHabits(): List<CommonHabit>

    suspend fun updateHabit(commonHabit: CommonHabit)

}