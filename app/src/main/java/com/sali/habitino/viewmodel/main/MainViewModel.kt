package com.sali.habitino.viewmodel.main

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.dto.CommonHabit
import com.sali.habitino.model.dto.SelfAddedHabit
import com.sali.habitino.model.repo.CommonHabitRepo
import com.sali.habitino.model.repo.ScoreRepo
import com.sali.habitino.model.repo.SelfAddedHabitRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val scoreRepo: ScoreRepo,
    private val commonHabitRepo: CommonHabitRepo,
    private val selfAddedHabitRepo: dagger.Lazy<SelfAddedHabitRepo>
) : ViewModel() {

    private val _mainScreenState =
        MutableStateFlow(MainScreenState())
    val mainScreenState: StateFlow<MainScreenState>
        get() = _mainScreenState.asStateFlow()

    fun onAction(action: MainActions) {
        when (action) {
            is MainActions.GetScore -> getScore()

            is MainActions.GetCommonHabits -> getCommonHabits()

            is MainActions.GetSelfAddedHabits -> getSelfAddedHabits()

            is MainActions.AddHabit -> addHabit(
                action.title,
                action.description,
                action.solution,
                action.state,
                action.tags
            )

            is MainActions.DeleteHabit -> deleteSelfAddedHabit(action.selfAddedHabit)

            is MainActions.UpdateCommonHabit -> {
                saveScore(action.score)
                updateCommonHabit(action.commonHabit)
                getScore()
            }

            is MainActions.UpdateSelfAddedHabit -> {
                saveScore(action.score)
                updateSelfAddedHabit(action.selfAddedHabit)
                getScore()
            }

            is MainActions.SearchByTag -> {}

            else -> Unit
        }
    }

    private fun getScore() = viewModelScope.launch(Dispatchers.IO) {
        _mainScreenState.update { it.copy(score = scoreRepo.getScore()) }
    }

    private fun saveScore(score: Int) = viewModelScope.launch {
        scoreRepo.saveScore(score)
    }

    private fun getCommonHabits() = updateMainScreenState(
        scope = viewModelScope,
        dispatcher = Dispatchers.IO,
        state = _mainScreenState
    ) {
        _mainScreenState.update {
            it.copy(
                commonHabits = commonHabitRepo.getAllHabits().toMutableStateList(),
                loading = false
            )
        }
    }

    private fun updateCommonHabit(commonHabit: CommonHabit) = updateMainScreenState(
        scope = viewModelScope,
        dispatcher = Dispatchers.IO,
        state = _mainScreenState
    ) {
        commonHabitRepo.updateHabit(commonHabit)
        _mainScreenState.update {
            it.copy(
                score = it.score,
                commonHabits = commonHabitRepo.getAllHabits().toMutableStateList(),
                loading = false
            )
        }
    }

    private fun getSelfAddedHabits() = updateMainScreenState(
        scope = viewModelScope,
        dispatcher = Dispatchers.IO,
        state = _mainScreenState
    ) {
        _mainScreenState.update {
            it.copy(
                selfAddedHabits = selfAddedHabitRepo.get().getAllHabits().toMutableStateList(),
                loading = false
            )
        }
    }

    private fun updateSelfAddedHabit(selfAddedHabit: SelfAddedHabit) =
        updateMainScreenState(
            scope = viewModelScope,
            dispatcher = Dispatchers.IO,
            state = _mainScreenState
        ) {
            selfAddedHabitRepo.get().updateHabit(selfAddedHabit)
            _mainScreenState.update {
                it.copy(
                    selfAddedHabits = selfAddedHabitRepo.get().getAllHabits().toMutableStateList(),
                    loading = false
                )
            }
        }

    private fun addHabit(
        title: String,
        description: String,
        solution: String,
        state: Boolean,
        tags: List<String>
    ) = updateMainScreenState(
        scope = viewModelScope,
        dispatcher = Dispatchers.IO,
        state = _mainScreenState
    ) {
        selfAddedHabitRepo.get().insert(
            title = title,
            description = description,
            solution = solution,
            state = state,
            tags = tags
        )
        _mainScreenState.update {
            it.copy(
                selfAddedHabits = selfAddedHabitRepo.get().getAllHabits().toMutableStateList(),
                loading = false
            )
        }
    }

    private fun deleteSelfAddedHabit(selfAddedHabit: SelfAddedHabit) =
        updateMainScreenState(
            scope = viewModelScope,
            dispatcher = Dispatchers.IO,
            state = _mainScreenState
        ) {
            selfAddedHabitRepo.get().delete(selfAddedHabit)
            _mainScreenState.update {
                it.copy(
                    selfAddedHabits = selfAddedHabitRepo.get().getAllHabits().toMutableStateList(),
                    loading = false
                )
            }
        }
}

fun updateMainScreenState(
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    state: MutableStateFlow<MainScreenState>,
    useCase: suspend () -> Unit
) {
    scope.launch(dispatcher) {
        state.update { it.copy(loading = true) }
        Log.d("State loading", state.value.toString())
        try {
            Log.d("State answer", state.value.toString())
            useCase.invoke()
        } catch (exception: Exception) {
            val message = exception.message ?: "Something went wrong"
            state.update { it.copy(loading = false, error = message) }
            Log.d("State error", state.value.toString())
        }
    }
}