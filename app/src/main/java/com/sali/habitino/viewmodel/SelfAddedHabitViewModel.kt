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

    private var _selfAddedHabits = MutableStateFlow<List<SelfAddedHabit>>(emptyList())
    var selfAddedHabits = _selfAddedHabits.asStateFlow()
    fun getAllSelfAddedHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            _selfAddedHabits.value = selfAddedHabitRepo.getAllHabits()
        }
    }

    private var _selfAddedHabit = MutableStateFlow<SelfAddedHabit?>(null)
    var selfAddedHabit = _selfAddedHabit.asStateFlow()
    fun getSelfAddedHabit(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _selfAddedHabit.value = selfAddedHabitRepo.getSelfAddedHabit(title)
        }
    }

    fun insertSelfAddedHabit(selfAddedHabit: SelfAddedHabit) {
        viewModelScope.launch(Dispatchers.IO) {
            selfAddedHabitRepo.insert(selfAddedHabit)
        }
    }

    fun deleteSelfAddedHabit(selfAddedHabit: SelfAddedHabit) {
        viewModelScope.launch(Dispatchers.IO) {
            selfAddedHabitRepo.delete(selfAddedHabit)
        }
    }

}