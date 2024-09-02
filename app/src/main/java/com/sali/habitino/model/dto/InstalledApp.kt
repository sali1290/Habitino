package com.sali.habitino.model.dto

import android.graphics.drawable.Drawable

data class InstalledApp(

    val name: String,

    val appIcon: Drawable,

    val status: Int,

    val packageName: String,

    )