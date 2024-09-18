package com.sali.habitino.view.utile

import android.Manifest
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.accessibility.AccessibilityManager

fun Context.checkAppPermissions(): Boolean {

    return when {
        !Settings.canDrawOverlays(this) -> false

        !checkAccessibilityPermission(this) -> false

        else -> true
    }
}

fun Context.grantAppRequiredPermissions() {
    when {
        !Settings.canDrawOverlays(this) -> {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${this.packageName}")
            )
            this.startActivity(intent)
        }

        !checkAccessibilityPermission(this) -> {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            this.startActivity(intent)
        }

    }
}

private fun checkAccessibilityPermission(context: Context): Boolean {
    var isAccessibilityEnabled = false
    (context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager).apply {
        installedAccessibilityServiceList.forEach { installedService ->
            installedService.resolveInfo.serviceInfo.apply {
                if (getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK).any {
                        it.resolveInfo.serviceInfo.packageName == packageName &&
                                it.resolveInfo.serviceInfo.name == name &&
                                permission == Manifest.permission.BIND_ACCESSIBILITY_SERVICE &&
                                it.resolveInfo.serviceInfo.packageName == this.packageName
                    }
                )
                    isAccessibilityEnabled = true
            }
        }
    }
    return isAccessibilityEnabled
}