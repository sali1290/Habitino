package com.sali.habitino.viewmodel.main

import com.sali.habitino.model.dto.Habit
import com.sali.habitino.model.dto.SelfAddedHabit

sealed class MainActions {
    data object GetScore : MainActions()

    data object GetCommonHabits : MainActions()

    data object GetSelfAddedHabits : MainActions()

    data class UpdateCommonHabit(val score: Int, val habit: Habit) : MainActions()

    data class UpdateSelfAddedHabit(val score: Int, val selfAddedHabit: SelfAddedHabit) :
        MainActions()

    data class AddHabit(
        val title: String,
        val description: String,
        val solution: String,
        val state: Boolean,
        val tags: List<String>
    ) : MainActions()

    data class DeleteHabit(val selfAddedHabit: SelfAddedHabit) : MainActions()

    data object ShowTags : MainActions()

    data object SearchByTag : MainActions()
}