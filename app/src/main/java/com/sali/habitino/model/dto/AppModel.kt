package com.sali.habitino.model.dto

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class AppModel(
//    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,

//    @ColumnInfo(name = "app_icon")
    val appIcon: Drawable,

//    @ColumnInfo(name = "package_name")
    val packageName: String
)
