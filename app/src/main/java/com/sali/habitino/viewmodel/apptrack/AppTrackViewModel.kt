package com.sali.habitino.viewmodel.apptrack

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.dto.AppModel
import com.sali.habitino.model.repo.AppTrackRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppTrackViewModel @Inject constructor(private val appTrackRepo: AppTrackRepo) :
    ViewModel() {

    private val _appTrackState = MutableStateFlow(AppTrackScreenState())
    val appTrackState: StateFlow<AppTrackScreenState>
        get() = _appTrackState

    fun onAction(action: AppTrackAction) {
        when (action) {
            is AppTrackAction.GetSavedApps -> getSavedApps()
            is AppTrackAction.AddApp -> addApp(action.appModel)
            is AppTrackAction.RemoveApp -> removeApp(action.appModel)
        }
    }

    private fun getSavedApps() =
        updateAppTrackScreenState(scope = viewModelScope, state = _appTrackState) {
            _appTrackState.update {
                it.copy(savedApps = appTrackRepo.getAllSavedApps().toMutableStateList())
            }
        }

    private fun addApp(appModel: AppModel) = viewModelScope.launch(Dispatchers.IO) {
        appTrackRepo.addApp(appModel)
    }

    private fun removeApp(appModel: AppModel) = viewModelScope.launch(Dispatchers.IO) {
        appTrackRepo.removeApp(appModel)
    }

}

private fun updateAppTrackScreenState(
    scope: CoroutineScope,
    state: MutableStateFlow<AppTrackScreenState>,
    useCase: suspend () -> Unit
) {
    scope.launch(Dispatchers.IO) {
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