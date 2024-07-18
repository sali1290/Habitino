package com.sali.habitino.model.repo

import com.sali.habitino.model.db.SelfAddedHabitDao
import com.sali.habitino.model.dto.SelfAddedHabit
import com.sali.habitino.model.dto.Tags
import javax.inject.Inject

class SelfAddedHabitRepoImpl @Inject constructor(private val selfAddedHabitDao: SelfAddedHabitDao) :
    SelfAddedHabitRepo {
    override suspend fun insert(
        title: String,
        description: String,
        solution: String,
        state: Boolean,
        tags: List<String>
    ) {
        val habit = SelfAddedHabit(
            id = 0,
            title = title,
            description = description,
            solution = solution,
            state = if (state) "good" else "bad",
            tags = Tags(tags),
            isCompleted = false,
            lastCompletedDate = null
        )
        selfAddedHabitDao.insert(habit)
    }

    override suspend fun delete(habit: SelfAddedHabit) {
        selfAddedHabitDao.delete(habit)
    }

    override suspend fun getAllHabits(): List<SelfAddedHabit> {
        return selfAddedHabitDao.getAll()
    }

    override suspend fun getSelfAddedHabit(title: String): SelfAddedHabit {
        return selfAddedHabitDao.findByTitle(title)
    }

    override suspend fun updateHabit(habit: SelfAddedHabit) {
        selfAddedHabitDao.updateHabit(habit)
    }

}