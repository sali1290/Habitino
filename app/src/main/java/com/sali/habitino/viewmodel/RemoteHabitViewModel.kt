package com.sali.habitino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.dto.Habit
import com.sali.habitino.model.repo.RemoteHabitRepo
import com.sali.habitino.view.utils.BackgroundTaskResult
import com.sali.habitino.view.utils.updateInBackground
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteHabitViewModel @Inject constructor(private val remoteHabitRepo: RemoteHabitRepo) :
    ViewModel() {

    private val _habits = MutableStateFlow<BackgroundTaskResult<List<Habit>>>(
        BackgroundTaskResult(result = emptyList(), loading = true)
    )
    var habits = _habits.asStateFlow()
    fun getAllHabits() = updateInBackground(flow = _habits) {
        remoteHabitRepo.getAllHabits()
    }

    fun updateHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        remoteHabitRepo.updateHabit(habit)
        getAllHabits()
    }
}