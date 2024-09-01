package com.sali.habitino.model.dto

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,

    @ColumnInfo(name = "app_icon", typeAffinity = ColumnInfo.BLOB)
    val appIcon: Drawable,

    var status: Int, // if equals to 1 that means app is saved

    @ColumnInfo(name = "package_name")
    val packageName: String
)
