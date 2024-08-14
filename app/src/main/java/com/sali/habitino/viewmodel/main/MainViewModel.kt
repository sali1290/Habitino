package com.sali.habitino.viewmodel.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.dto.Habit
import com.sali.habitino.model.dto.SelfAddedHabit
import com.sali.habitino.model.repo.RemoteHabitRepo
import com.sali.habitino.model.repo.ScoreRepo
import com.sali.habitino.model.repo.SelfAddedHabitRepo
import com.sali.habitino.view.utils.ScreenState
import com.sali.habitino.view.utils.updateScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val scoreRepo: ScoreRepo,
    private val remoteHabitRepo: RemoteHabitRepo,
    private val selfAddedHabitRepo: SelfAddedHabitRepo
) : ViewModel() {

    private val _mainScreenState =
        MutableStateFlow(ScreenState(result = MainScreenState()))
    val mainScreenState: StateFlow<ScreenState<MainScreenState>>
        get() = _mainScreenState.asStateFlow()

    fun onAction(event: MainActions) {
        when (event) {
            is MainActions.GetScore -> {
                getScore()
            }

            is MainActions.GetCommonHabits -> {
                getCommonHabits()
            }

            is MainActions.GetSelfAddedHabits -> {
                getSelfAddedHabits()
            }

            is MainActions.AddHabit -> {
                addHabit(event.title, event.description, event.solution, event.state, event.tags)
            }

            is MainActions.DeleteHabit -> {
                deleteSelfAddedHabit(event.selfAddedHabit)
            }

            is MainActions.UpdateCommonHabit -> {
                saveScore(event.score)
                updateCommonHabit(event.habit)
            }

            is MainActions.UpdateSelfAddedHabit -> {
                saveScore(event.score)
                updateSelfAddedHabit(event.selfAddedHabit)
            }

            is MainActions.SearchByTag -> {}

            else -> Unit
        }
    }

    private fun getScore() = updateScreenState(state = _mainScreenState) {
        _mainScreenState.update {
            ScreenState(result = MainScreenState(score = scoreRepo.getScore()))
        }
    }

    private fun saveScore(score: Int) = viewModelScope.launch {
        scoreRepo.saveScore(score)
    }

    private fun getCommonHabits() = updateScreenState(state = _mainScreenState) {
        _mainScreenState.update {
            ScreenState(result = MainScreenState(commonHabits = remoteHabitRepo.getAllHabits()))
        }
    }

    private fun updateCommonHabit(habit: Habit) = updateScreenState(state = _mainScreenState) {
        remoteHabitRepo.updateHabit(habit)
        _mainScreenState.update {
            ScreenState(result = MainScreenState(commonHabits = remoteHabitRepo.getAllHabits()))
        }
    }

    private fun getSelfAddedHabits() = updateScreenState(state = _mainScreenState) {
        _mainScreenState.update {
            ScreenState(result = MainScreenState(selfAddedHabits = selfAddedHabitRepo.getAllHabits()))
        }
    }

    private fun updateSelfAddedHabit(selfAddedHabit: SelfAddedHabit) =
        updateScreenState(state = _mainScreenState) {
            selfAddedHabitRepo.updateHabit(selfAddedHabit)
            _mainScreenState.update { it.copy(result = MainScreenState(selfAddedHabits = selfAddedHabitRepo.getAllHabits())) }
        }

    private fun addHabit(
        title: String,
        description: String,
        solution: String,
        state: Boolean,
        tags: List<String>
    ) = updateScreenState(state = _mainScreenState) {
        selfAddedHabitRepo.insert(
            title = title,
            description = description,
            solution = solution,
            state = state,
            tags = tags
        )
        _mainScreenState.update { it.copy(result = MainScreenState(selfAddedHabits = selfAddedHabitRepo.getAllHabits())) }
    }

    private fun deleteSelfAddedHabit(selfAddedHabit: SelfAddedHabit) =
        updateScreenState(state = _mainScreenState) {
            selfAddedHabitRepo.delete(selfAddedHabit)
            _mainScreenState.update { it.copy(result = MainScreenState(selfAddedHabits = selfAddedHabitRepo.getAllHabits())) }
        }
}