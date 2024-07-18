package com.sali.habitino.model.dto

import androidx.room.Entity

@Entity
data class Tags(
    val names: List<String>
)
