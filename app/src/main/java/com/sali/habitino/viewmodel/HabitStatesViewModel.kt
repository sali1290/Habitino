package com.sali.habitino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.repo.HabitStateRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitStatesViewModel @Inject constructor(private val habitStateRepo: HabitStateRepo) :
    ViewModel() {

    fun checkAllHabitsState() = viewModelScope.launch(Dispatchers.IO) {
        habitStateRepo.checkAllHabitsState()
    }

}