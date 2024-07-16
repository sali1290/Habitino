package com.sali.habitino.view.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class BackgroundTaskResult<T>(
    val loading: Boolean = false,
    val error: String? = null,
    val result: T
)

fun <T> ViewModel.updateInBackground(
    flow: MutableStateFlow<BackgroundTaskResult<T>>,
    scope: CoroutineScope = viewModelScope,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    useCase: suspend () -> T
) {
    scope.launch(dispatcher) {
        flow.update { it.copy(loading = true, error = null) }
        try {
            val result = useCase.invoke()
            flow.update { BackgroundTaskResult(result = result) }
        } catch (exception: Exception) {
            val message = exception.message ?: "Something went wrong"
            flow.update { it.copy(loading = false, error = message) }
        }
    }
}