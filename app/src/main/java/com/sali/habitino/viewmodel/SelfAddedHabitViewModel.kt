package com.sali.habitino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.dto.SelfAddedHabit
import com.sali.habitino.model.repo.SelfAddedHabitRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelfAddedHabitViewModel @Inject constructor(private val selfAddedHabitRepo: SelfAddedHabitRepo) :
    ViewModel() {

    private var _habits = MutableStateFlow<List<SelfAddedHabit>>(emptyList())
    var habits = _habits.asStateFlow()
    fun getAllSelfAddedHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            _habits.value = selfAddedHabitRepo.getAllHabits()
        }
    }

    fun updateHabit(habit: SelfAddedHabit) =
        viewModelScope.launch(Dispatchers.IO) {
            selfAddedHabitRepo.updateHabit(habit)
            getAllSelfAddedHabits()
        }


    fun insertSelfAddedHabit(title: String, description: String, solution: String, state: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            selfAddedHabitRepo.insert(
                title = title,
                description = description,
                solution = solution,
                state = state
            )
            getAllSelfAddedHabits()
        }


    fun deleteSelfAddedHabit(habit: SelfAddedHabit) =
        viewModelScope.launch(Dispatchers.IO) {
            selfAddedHabitRepo.delete(habit)
        }


}