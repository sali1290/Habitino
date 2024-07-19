package com.sali.habitino.model.repo

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sali.habitino.R
import com.sali.habitino.model.db.HabitDao
import com.sali.habitino.model.dto.Habit
import com.sali.habitino.model.dto.Tags
import com.sali.habitino.model.utils.DataSource
import com.sali.habitino.model.utils.Keys
import com.sali.habitino.model.utils.SharedPrefUtils
import com.sali.habitino.model.utils.TagKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Suppress("UNCHECKED_CAST")
class RemoteHabitRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val habitDao: HabitDao,
    private val sharedPrefUtils: SharedPrefUtils,
    @ApplicationContext private val context: Context
) : RemoteHabitRepo {

    override suspend fun getAllHabits(): List<Habit> {
        val habitList = mutableListOf<Habit>()
        when (chooseDataSource()) {

            DataSource.Remote -> {
                habitList.addAll(fetchRemoteHabits())
                saveHabitsToLocalDataSource(habitList)
            }

            DataSource.Local -> habitList.addAll(fetchLocalHabit())

        }
        return habitList
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.upsert(habit)
    }

    private suspend fun fetchRemoteHabits() = suspendCoroutine<List<Habit>> { continuation ->
        val habitsList = mutableListOf<Habit>()
        firestore.collection(Keys.HABIT_COLLECTION).get()
            .addOnSuccessListener { documents ->
                Log.d(TagKeys.FIRESTORE_ANSWER, documents.toString())
                for (document in documents) {
                    val habitMap =
                        document.data.getValue(
                            document.data.keys.toString().removeSurrounding("[", "]")
                        ) as HashMap<String, *>
                    val habit = Habit(
                        id = document.id,
                        title = document.data.keys.toString().removeSurrounding("[", "]"),
                        description = habitMap.getValue(Keys.HABIT_DESCRIPTION).toString(),
                        solution = habitMap.getValue(Keys.HABIT_SOLUTION) as? String,
                        state = habitMap.getValue(Keys.HABIT_STATE).toString(),
                        tags = Tags(habitMap.getValue(Keys.HABIT_TAGS) as List<String>),
                        isCompleted = false,
                        lastCompletedDate = null
                    )
                    habitsList.add(habit)
                }
                sharedPrefUtils.saveHabitsFetchDate()
            }
            .addOnCompleteListener {

                if (!it.isSuccessful) {
                    Log.d(
                        TagKeys.FIRESTORE_ANSWER,
                        context.getString(R.string.connection_error_please_try_again)
                    )
                    val exception =
                        IOException(context.getString(R.string.connection_error_please_try_again))
                    continuation.resumeWithException(exception)
                } else {
                    continuation.resume(habitsList)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TagKeys.FIRESTORE_ANSWER, exception.message.toString())
                throw exception
            }
    }

    private fun fetchLocalHabit(): List<Habit> {
        return habitDao.getAll()
    }

    private fun saveHabitsToLocalDataSource(habits: List<Habit>) {
        habits.forEach {
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