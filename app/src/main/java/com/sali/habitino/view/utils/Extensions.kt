package com.sali.habitino.view.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ScreenState<T>(
    val loading: Boolean = false,
    val error: String? = null,
    val result: T
)

fun <T> ViewModel.updateScreenState(
    scope: CoroutineScope = viewModelScope,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    state: MutableStateFlow<ScreenState<T>>,
    useCase: suspend () -> Unit
) {
    scope.launch(dispatcher) {
        state.update { it.copy(loading = true, error = null) }
        try {
            useCase.invoke()
        } catch (exception: Exception) {
            val message = exception.message ?: "Something went wrong"
            state.update { it.copy(loading = false, error = message) }
        }
    }
}