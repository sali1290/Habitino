package com.sali.habitino.model.repo

interface HabitStateRepo {

    suspend fun checkAllHabitsState()

}