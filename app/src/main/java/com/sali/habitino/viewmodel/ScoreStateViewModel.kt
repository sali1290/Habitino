package com.sali.habitino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.repo.ScoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreStateViewModel @Inject constructor(private val scoreRepo: ScoreRepo) : ViewModel() {

    fun saveScore(score: Int) = viewModelScope.launch {
        scoreRepo.saveScore(score)
    }

    private var _score = MutableStateFlow(0)
    val score: StateFlow<Int>
        get() = _score.asStateFlow()

    fun getScore() = viewModelScope.launch {
        _score.value = scoreRepo.getScore()
    }

}