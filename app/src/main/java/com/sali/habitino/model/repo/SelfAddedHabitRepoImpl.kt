package com.sali.habitino.model.repo

import com.sali.habitino.model.db.SelfAddedHabitDao
import com.sali.habitino.model.dto.SelfAddedHabit
import javax.inject.Inject

class SelfAddedHabitRepoImpl @Inject constructor(private val selfAddedHabitDao: SelfAddedHabitDao) :
    SelfAddedHabitRepo {
    override suspend fun insert(selfAddedHabit: SelfAddedHabit) {
        selfAddedHabitDao.insert(selfAddedHabit)
    }

    override suspend fun delete(selfAddedHabit: SelfAddedHabit) {
        selfAddedHabitDao.delete(selfAddedHabit)
    }

    override suspend fun getAllHabits(): List<SelfAddedHabit> {
        return selfAddedHabitDao.getAll()
    }

    override suspend fun getSelfAddedHabit(title: String): SelfAddedHabit {
        return selfAddedHabitDao.findByTitle(title)
    }
}