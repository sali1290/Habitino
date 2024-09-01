package com.sali.habitino.viewmodel.installedapps

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
class InstalledAppsViewModel @Inject constructor(private val appTrackRepo: AppTrackRepo) :
    ViewModel() {

    private val _installedAppsState = MutableStateFlow(InstalledAppsState())
    val installedAppsState: StateFlow<InstalledAppsState>
        get() = _installedAppsState

    fun onAction(action: InstalledAppsAction) {
        when (action) {
            is InstalledAppsAction.GetAllInstalledApps -> getAllInstalledApps()
            is InstalledAppsAction.GetSavedApps -> getSavedApps()
            is InstalledAppsAction.AddApp -> addApp(action.appModel)
            is InstalledAppsAction.RemoveApp -> removeApp(action.appModel)
        }
    }

    private fun getAllInstalledApps() =
        updateInstalledScreenState(scope = viewModelScope, state = _installedAppsState) {
            _installedAppsState.update {
                it.copy(installedApps = appTrackRepo.getAllInstalledApps().toMutableStateList())
            }
        }

    private fun getSavedApps() =
        updateInstalledScreenState(scope = viewModelScope, state = _installedAppsState) {
            _installedAppsState.update {
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

private fun updateInstalledScreenState(
    scope: CoroutineScope,
    state: MutableStateFlow<InstalledAppsState>,
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