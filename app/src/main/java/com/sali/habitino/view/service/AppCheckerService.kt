package com.sali.habitino.view.service

import android.accessibilityservice.AccessibilityService
import android.app.AlertDialog
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import androidx.room.Room
import com.sali.habitino.R
import com.sali.habitino.model.db.AppDatabase
import com.sali.habitino.model.db.SavedAppDao
import com.sali.habitino.model.dto.SavedApp
import com.sali.habitino.view.util.LogKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppCheckerService : AccessibilityService() {

    private var lastPackageName: String? = null
    private var isDialogShown = false
    private val serviceScope = CoroutineScope(Dispatchers.IO)
    private lateinit var database: AppDatabase
    private lateinit var savedAppDao: SavedAppDao
    private var savedApps = emptyList<SavedApp>()

    override fun onCreate() {
        serviceScope.launch {
            database = Room.databaseBuilder(
                this@AppCheckerService,
                AppDatabase::class.java, "habitino-db"
            ).build()
            savedAppDao = database.savedAppDao()
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        serviceScope.launch {
            savedApps = savedAppDao.getAll()
        }
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName.toString()
            Log.d(LogKeys.ACCESSIBILITY_SERVICE, "Package name: $packageName")
            Log.d(LogKeys.ACCESSIBILITY_SERVICE, "Saved package name: $savedApps")

            if (savedApps.isNotEmpty()) {
                for (x in savedApps.indices) {
                    if (isDialogShown)
                        break

                    if (lastPackageName == savedApps[x].packageName)
                        break

                    if (savedApps[x].packageName == packageName && !isDialogShown) {
                        isDialogShown = true
                        showDialog(savedApps[x].message)
                        break
                    }
                }
                if (packageName != "com.sali.habitino")
                    lastPackageName = packageName
            }
        }
    }

    private fun showDialog(message: String) {
        val builder = AlertDialog.Builder(applicationContext)
        builder.setTitle(getString(R.string.be_aware))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                isDialogShown = false
            }
        val dialog = builder.create()
        dialog.window?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        dialog.show()
    }

    override fun onInterrupt() {
        Log.d(LogKeys.ACCESSIBILITY_SERVICE, "on interrupt")
    }
}