package com.sali.habitino.model.repo

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sali.habitino.model.db.HabitDao
import com.sali.habitino.model.dto.Habit
import com.sali.habitino.model.dto.Tags
import com.sali.habitino.model.utils.Keys
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
    @ApplicationContext private val context: Context
) : RemoteHabitRepo {

    override suspend fun getAllHabits(): List<Habit> {
        val habitList = mutableListOf<Habit>()
        habitList.addAll(getAllSavedHabit())
        if (habitList.isEmpty() || chooseDataSource(context)) {
            habitList.addAll(getAllRemoteHabits())
            saveAllHabits(habitList)
        }
        return habitList
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.upsert(habit)
    }

    private suspend fun getAllRemoteHabits() = suspendCoroutine<List<Habit>> { continuation ->
        val habitsList = mutableListOf<Habit>()
        firestore.collection("habits").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val habitMap =
                        document.data.getValue(
                            document.data.keys.toString().removeSurrounding("[", "]")
                        ) as HashMap<String, *>
                    val habit = Habit(
                        id = document.id,
                        title = document.data.keys.toString().removeSurrounding("[", "]"),
                        description = habitMap.getValue("description").toString(),
                        solution = habitMap.getValue("solution") as? String,
                        state = habitMap.getValue("state").toString(),
                        tags = Tags(habitMap.getValue("tags") as List<String>),
                        isCompleted = false,
                        lastCompletedDate = null
                    )
                    habitsList.add(habit)
                }
                saveRemoteFetchDate(context)
            }
            .addOnCompleteListener {
                val exception = IOException("Connection error, please try again")
                if (!it.isSuccessful) {
                    Log.d("firestore answer", "Connection error, please try again")
                    continuation.resumeWithException(exception)
                } else {
                    continuation.resume(habitsList)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("firestore answer", exception.message.toString())
                throw exception
            }
    }

    private fun getAllSavedHabit(): List<Habit> {
        return habitDao.getAll()
    }

    private fun saveAllHabits(habits: List<Habit>) {
        habits.forEach {
            habitDao.upsert(it)
        }
    }

    private fun saveRemoteFetchDate(context: Context) {
        context.getSharedPreferences(Keys.HABITINO_SHARED, Context.MODE_PRIVATE).edit()
            .putInt(Keys.FETCH_DATE, LocalDateTime.now().dayOfYear).apply()
    }

    private fun chooseDataSource(context: Context): Boolean {
        val fetchDate: Int =
            context.getSharedPreferences(Keys.HABITINO_SHARED, Context.MODE_PRIVATE)
                .getInt(Keys.FETCH_DATE, -1)
        val currentDate = LocalDateTime.now().dayOfYear
        // Fetch data every 30 days from firestore
        return fetchDate == -1 || fetchDate + 30 < currentDate
    }
}