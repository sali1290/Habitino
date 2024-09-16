package com.sali.habitino.view.service

import android.accessibilityservice.AccessibilityService
import android.app.AlertDialog
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.sali.habitino.view.utile.LogKeys


class AppCheckerService : AccessibilityService() {

    private var isDialogShown = false
    private var lastDialogTime: Long = 0
    private val dialogInterval = 5000 // 5 seconds

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName.toString()
            Log.d(LogKeys.ACCESSIBILITY_SERVICE, "Package Name: $packageName")
            // Implement checking the package name of saved apps by user by the app is now open
            if (!isDialogShown) {
                val currentTime = System.currentTimeMillis()
                if (
                    currentTime - lastDialogTime > dialogInterval &&
                    packageName == "com.google.android.dialer"
                ) {
                    isDialogShown = true
                    lastDialogTime = currentTime
                    showDialog()
                }
            }
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(applicationContext)
        builder.setTitle("Alert")
            .setMessage("You are entering the dialer app")
            .setPositiveButton("OK") { _, _ ->
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