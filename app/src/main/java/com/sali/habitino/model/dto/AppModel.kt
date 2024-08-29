package com.sali.habitino.model.dto

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class AppModel(
    val name: String,

    @ColumnInfo(name = "app_icon")
    val appIcon: Drawable,

    val status: Int, // If status is 0 then app has a message

    @ColumnInfo(name = "package_name")
    val packageName: String
)
