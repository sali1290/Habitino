package com.sali.habitino.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sali.habitino.model.dto.SelfAddedHabit

@Dao
interface SelfAddedHabitDao {

    @Query("SELECT * FROM SelfAddedHabit")
    fun getAll(): List<SelfAddedHabit>

    @Query("SELECT * FROM selfaddedhabit WHERE title LIKE :title LIMIT 1")
    fun findByTitle(title: String): SelfAddedHabit

    @Insert
    fun insert(selfAddedHabit: SelfAddedHabit)

    @Delete
    fun delete(selfAddedHabit: SelfAddedHabit)

}