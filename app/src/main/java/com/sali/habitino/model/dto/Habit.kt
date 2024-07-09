package com.sali.habitino.model.dto

import java.time.LocalDate

data class Habit(
    val id: String,
    var isCompleted: Boolean? = null,
    var lastCompletedDate: LocalDate? = null
)
