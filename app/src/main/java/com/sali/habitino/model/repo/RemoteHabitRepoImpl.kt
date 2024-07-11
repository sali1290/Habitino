package com.sali.habitino.model.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sali.habitino.model.dto.Habit
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteHabitRepoImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    RemoteHabitRepo {
    override fun getAllRemoteHabits() = flow {
        val habitsList = mutableListOf<Habit>()
        firestore.collection("habits").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("firestore answer", "${document.data}")
                    val habit =
                        document.data.getValue(
                            document.data.keys.toString().removeSurrounding("[", "]")
                        ) as HashMap<String, *>
                    habitsList.add(
                        Habit(
                            id = document.id,
                            title = document.data.keys.toString().removeSurrounding("[", "]"),
                            description = habit.getValue("description").toString(),
                            solution = habit.getValue("solution") as? String,
                            state = habit.getValue("state").toString(),
                            isCompleted = null,
                            lastCompletedDate = null
                        )
                    )
                }
            }.await()
        emit(habitsList)
    }
}