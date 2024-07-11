package com.sali.habitino.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.time.LocalDate

@Entity
data class Habit(
    var id: String,

    var title: String,

    var description: String,

    var solution: String? = null,

    var state: String,

    @ColumnInfo(name = "is_completed")
    var isCompleted: Boolean? = null,

    @ColumnInfo(name = "last_completed_date")
    var lastCompletedDate: LocalDate? = null
)
