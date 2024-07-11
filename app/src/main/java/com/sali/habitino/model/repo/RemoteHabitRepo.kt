package com.sali.habitino.model.repo

import com.sali.habitino.model.dto.Habit
import kotlinx.coroutines.flow.Flow

interface RemoteHabitRepo {

    fun getAllRemoteHabits(): Flow<List<Habit>>

}