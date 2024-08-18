package com.sali.habitino.model.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sali.habitino.model.dto.CommonHabit

@Dao
interface HabitDao {

    @Query("SELECT * FROM CommonHabit")
    fun getAll(): List<CommonHabit>

    @Upsert
    fun upsert(commonHabit: CommonHabit)

}