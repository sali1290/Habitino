package com.sali.habitino.viewmodel.main

import com.sali.habitino.model.dto.CommonHabit
import com.sali.habitino.model.dto.SelfAddedHabit

data class MainScreenState(
    val score: Int = 0,
    val commonHabits: List<CommonHabit>? = null,
    val selfAddedHabits: List<SelfAddedHabit> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
)