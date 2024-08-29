package com.sali.habitino.model.repo

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sali.habitino.R
import com.sali.habitino.model.db.HabitDao
import com.sali.habitino.model.dto.CommonHabit
import com.sali.habitino.model.dto.Tags
import com.sali.habitino.model.utile.ConnectivityManager
import com.sali.habitino.model.utile.DataSource
import com.sali.habitino.model.utile.Keys
import com.sali.habitino.model.utile.SharedPrefUtils
import com.sali.habitino.model.utile.TagKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CommonHabitRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val habitDao: HabitDao,
    private val sharedPrefUtils: SharedPrefUtils,
    @ApplicationContext private val context: Context,
    private val connectivityManager: ConnectivityManager
) : CommonHabitRepo {

    override suspend fun getAllHabits(): List<CommonHabit> {
        val commonHabitList = mutableListOf<CommonHabit>()
        commonHabitList.addAll(fetchLocalHabit())
        return if (commonHabitList.isNotEmpty() && chooseDataSource() == DataSource.Local) {
            Log.d(TagKeys.FIRESTORE_ANSWER, "Data fetched from local data source")
            commonHabitList
        } else {
            commonHabitList.apply {
                clear()
                addAll(fetchRemoteHabits())
            }
            Log.d(TagKeys.FIRESTORE_ANSWER, "Data fetched from remote data source")
            saveHabitsToLocalDataSource(commonHabitList)
            commonHabitList
        }
    }

    override suspend fun updateHabit(commonHabit: CommonHabit) {
        habitDao.upsert(commonHabit)
    }

    private suspend fun fetchRemoteHabits(): List<CommonHabit> {
        if (connectivityManager.isNetworkConnected()) {
            try {
                val habitsCollection = firestore.collection(Keys.HABIT_COLLECTION).get()
                val habits = habitsCollection.await().map {
                    val habitMap =
                        it.data.getValue(
                            it.data.keys.toString().removeSurrounding("[", "]")
                        ) as HashMap<String, *>
                    CommonHabit(
                        id = it.id,
                        title = it.data.keys.toString().removeSurrounding("[", "]"),
                        description = habitMap.getValue(Keys.HABIT_DESCRIPTION).toString(),
                        solution = habitMap.getValue(Keys.HABIT_SOLUTION) as? String,
                        state = habitMap.getValue(Keys.HABIT_STATE).toString(),
                        tags = Tags(habitMap.getValue(Keys.HABIT_TAGS) as List<String>),
                        isCompleted = false,
                        lastCompletedDate = null
                    )
                }
                return habits
            } catch (exception: Exception) {
                throw IOException(
                    exception.message
                        ?: context.getString(R.string.something_went_wrong_please_try_again)
                )
            }
        } else {
            throw IOException(context.getString(R.string.please_check_your_internet_connection))
        }
    }

    private fun fetchLocalHabit(): List<CommonHabit> {
        return habitDao.getAll()
    }

    private fun saveHabitsToLocalDataSource(commonHabits: List<CommonHabit>) {
        sharedPrefUtils.saveHabitsFetchDate()
        commonHabits.forEach {
            habitDao.upsert(it)
        }
    }

    private fun chooseDataSource(): DataSource {
        val fetchDate: Int = sharedPrefUtils.getHabitsFetchDate()
        val currentDate = LocalDateTime.now().dayOfYear
        // Fetch data every 30 days from firestore
        return if (fetchDate == -1 || fetchDate + 30 < currentDate)
            DataSource.Remote
        else
            DataSource.Local
    }
}