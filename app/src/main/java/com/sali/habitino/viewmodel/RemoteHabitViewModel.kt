package com.sali.habitino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.dto.Habit
import com.sali.habitino.model.repo.RemoteHabitRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteHabitViewModel @Inject constructor(private val remoteHabitRepo: RemoteHabitRepo) :
    ViewModel() {

    private val _habits = MutableStateFlow<List<Habit>>(emptyList())
    var habits = _habits.asStateFlow()

    fun getAllRemoteHabits() = viewModelScope.launch {
        remoteHabitRepo.getAllRemoteHabits().collect {
            _habits.value = it
        }
    }
}