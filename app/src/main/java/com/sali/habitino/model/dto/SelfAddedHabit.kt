package com.sali.habitino.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class SelfAddedHabit(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var title: String,

    var description: String,

    var solution: String? = null,

    var state: String,

    @ColumnInfo(name = "is_completed")
    var isCompleted: Boolean,

    @ColumnInfo(name = "last_completed_date")
    var lastCompletedDate: LocalDateTime? = null
)
