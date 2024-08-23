package com.sali.habitino.viewmodel.main

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sali.habitino.model.dto.CommonHabit
import com.sali.habitino.model.dto.SelfAddedHabit

data class MainScreenState(
    val score: Int = 0,
    val commonHabits: SnapshotStateList<CommonHabit>? = null,
    val selfAddedHabits: SnapshotStateList<SelfAddedHabit>? = null,
    val loading: Boolean = false,
    val error: String? = null,
)