package com.sali.habitino.viewmodel.main

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
    private val selfAddedHabitRepo: SelfAddedHabitRepo
) : ViewModel() {

    private val _mainScreenState =
        MutableStateFlow(MainScreenState())
    val mainScreenState: StateFlow<MainScreenState>
        get() = _mainScreenState.asStateFlow()

    fun onAction(event: MainActions) {
        when (event) {
            is MainActions.GetScore -> getScore()

            is MainActions.GetCommonHabits -> getCommonHabits()

            is MainActions.GetSelfAddedHabits -> getSelfAddedHabits()

            is MainActions.AddHabit -> addHabit(
                event.title,
                event.description,
                event.solution,
                event.state,
                event.tags
            )

            is MainActions.DeleteHabit -> deleteSelfAddedHabit(event.selfAddedHabit)

            is MainActions.UpdateCommonHabit -> {
                saveScore(event.score)
                updateCommonHabit(event.commonHabit)
                getScore()
            }

            is MainActions.UpdateSelfAddedHabit -> {
                saveScore(event.score)
                updateSelfAddedHabit(event.selfAddedHabit)
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
        _mainScreenState.update { it.copy(commonCommonHabits = commonHabitRepo.getAllHabits()) }
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
                commonCommonHabits = commonHabitRepo.getAllHabits()
            )
        }
    }

    private fun getSelfAddedHabits() = updateMainScreenState(
        scope = viewModelScope,
        dispatcher = Dispatchers.IO,
        state = _mainScreenState
    ) {
        _mainScreenState.update {
            it.copy(selfAddedHabits = selfAddedHabitRepo.getAllHabits())
        }
    }

    private fun updateSelfAddedHabit(selfAddedHabit: SelfAddedHabit) =
        updateMainScreenState(
            scope = viewModelScope,
            dispatcher = Dispatchers.IO,
            state = _mainScreenState
        ) {
            selfAddedHabitRepo.updateHabit(selfAddedHabit)
            _mainScreenState.update { it.copy(selfAddedHabits = selfAddedHabitRepo.getAllHabits()) }
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
        selfAddedHabitRepo.insert(
            title = title,
            description = description,
            solution = solution,
            state = state,
            tags = tags
        )
        _mainScreenState.update { it.copy(selfAddedHabits = selfAddedHabitRepo.getAllHabits()) }
    }

    private fun deleteSelfAddedHabit(selfAddedHabit: SelfAddedHabit) =
        updateMainScreenState(
            scope = viewModelScope,
            dispatcher = Dispatchers.IO,
            state = _mainScreenState
        ) {
            selfAddedHabitRepo.delete(selfAddedHabit)
            _mainScreenState.update { it.copy(selfAddedHabits = selfAddedHabitRepo.getAllHabits()) }
        }
}

fun updateMainScreenState(
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    state: MutableStateFlow<MainScreenState>,
    useCase: suspend () -> Unit
) {
    scope.launch(dispatcher) {
        state.update { it.copy(loading = true, error = null) }
        try {
            state.update { it.copy(loading = false) }
            useCase.invoke()
        } catch (exception: Exception) {
            val message = exception.message ?: "Something went wrong"
            state.update { it.copy(loading = false, error = message) }
        }
    }
}