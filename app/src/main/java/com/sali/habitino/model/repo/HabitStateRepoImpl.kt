package com.sali.habitino.model.repo

import com.sali.habitino.model.db.HabitDao
import com.sali.habitino.model.db.SelfAddedHabitDao
import java.time.LocalDateTime
import javax.inject.Inject

class HabitStateRepoImpl @Inject constructor(
    private val selfAddedHabitDao: SelfAddedHabitDao,
    private val habitDao: HabitDao
) :
    HabitStateRepo {
    override suspend fun checkAllHabitsState() {
        selfAddedHabitDao.getAll().forEach { habit ->
            habit.lastCompletedDate?.let {
                if (it.dayOfYear != LocalDateTime.now().dayOfYear) {
                    selfAddedHabitDao.updateHabit(habit.copy(isCompleted = false))
                }
            }
        }
        habitDao.getAll().forEach { habit ->
            habit.lastCompletedDate?.let {
                if (it.dayOfYear != LocalDateTime.now().dayOfYear) {
                    habitDao.upsert(habit.copy(isCompleted = false))
                }
            }
        }
    }
}