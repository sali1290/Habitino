package com.sali.habitino.view.service

import android.accessibilityservice.AccessibilityService
import android.app.AlertDialog
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.sali.habitino.R
import com.sali.habitino.model.dto.SavedApp
import com.sali.habitino.model.repo.AppsRepo
import com.sali.habitino.view.utile.LogKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppCheckerService : AccessibilityService() {

    private var isDialogShown = false
    private var lastDialogTime: Long = 0
    private val dialogInterval = 10000 // 10 seconds

    @Inject
    lateinit var appsRepo: AppsRepo
    private var savedApps = emptyList<SavedApp>()

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            savedApps = appsRepo.getAllSavedApps()
        }
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName.toString()
            Log.d(LogKeys.ACCESSIBILITY_SERVICE, "Package Name: $packageName")
            // Implement checking the package name of saved apps by user by the app is now open
            if (!isDialogShown) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastDialogTime > dialogInterval) {
                    savedApps.forEach {
                        if (it.packageName == packageName) {
                            isDialogShown = true
                            lastDialogTime = currentTime
                            showDialog(it.message)
                        }
                    }
                }
            }
        }
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(applicationContext)
        builder.setTitle(getString(R.string.be_aware))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                isDialogShown = false // Reset the flag when the dialog is dismissed
            }

        val dialog = builder.create()
        dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        dialog.show()
    }

    override fun onInterrupt() {
        Log.d(LogKeys.ACCESSIBILITY_SERVICE, "on interrupt")
    }
}