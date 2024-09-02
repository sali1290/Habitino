package com.sali.habitino.viewmodel.installedapps

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sali.habitino.model.dto.SavedApp
import com.sali.habitino.model.repo.AppsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstalledAppsViewModel @Inject constructor(private val appsRepo: AppsRepo) :
    ViewModel() {

    private val _installedAppsState = MutableStateFlow(InstalledAppsState())
    val installedAppsState: StateFlow<InstalledAppsState>
        get() = _installedAppsState

    fun onAction(action: InstalledAppsAction) {
        when (action) {
            is InstalledAppsAction.GetAllInstalledApps -> getAllInstalledApps()
            is InstalledAppsAction.GetSavedApps -> getSavedApps()
            is InstalledAppsAction.AddApp -> addApp(action.savedApp)
            is InstalledAppsAction.RemoveApp -> removeApp(action.savedApp)
        }
    }

    private fun getAllInstalledApps() =
        updateInstalledScreenState(scope = viewModelScope, state = _installedAppsState) {
            _installedAppsState.update {
                it.copy(installedApps = appsRepo.getAllInstalledApps().toMutableStateList())
            }
        }

    private fun getSavedApps() =
        updateInstalledScreenState(scope = viewModelScope, state = _installedAppsState) {
            _installedAppsState.update {
                it.copy(savedApps = appsRepo.getAllSavedApps().toMutableStateList())
            }
        }

    private fun addApp(savedApp: SavedApp) = viewModelScope.launch(Dispatchers.IO) {
        appsRepo.addApp(savedApp)
    }

    private fun removeApp(savedApp: SavedApp) = viewModelScope.launch(Dispatchers.IO) {
        appsRepo.removeApp(savedApp)
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