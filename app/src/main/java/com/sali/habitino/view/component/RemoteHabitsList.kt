package com.sali.habitino.view.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sali.habitino.viewmodel.RemoteHabitViewModel
import java.time.LocalDateTime

@Composable
fun RemoteHabitsList(onCompletedClick: (Int) -> Unit) {

    val remoteHabitViewModel: RemoteHabitViewModel = hiltViewModel()
    val remoteHabitsState by remoteHabitViewModel.habits.collectAsState()
    LaunchedEffect(key1 = Unit) {
        remoteHabitViewModel.getAllHabits()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        itemsIndexed(remoteHabitsState) { _, item ->
            HabitItem(
                title = item.title,
                description = item.description,
                solution = item.solution,
                state = item.state,
                isCompleted = item.isCompleted
            ) {
                if (!item.isCompleted) {
                    onCompletedClick(1)
                } else {
                    onCompletedClick(-1)
                }
                val updatedItem = item.copy(
                    isCompleted = !item.isCompleted,
                    lastCompletedDate = LocalDateTime.now()
                )

                remoteHabitViewModel.updateHabit(updatedItem)
            }
        }
    }

}