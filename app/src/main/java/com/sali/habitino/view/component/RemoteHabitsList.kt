package com.sali.habitino.view.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sali.habitino.viewmodel.RemoteHabitViewModel

@Composable
fun RemoteHabitsList() {

    val remoteHabitViewModel: RemoteHabitViewModel = hiltViewModel()
    LaunchedEffect(key1 = Unit) {
        remoteHabitViewModel.getAllRemoteHabits()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        itemsIndexed(remoteHabitViewModel.habits.value) { _, item ->
            HabitItem(
                title = item.title,
                description = item.description,
                solution = item.solution,
                state = item.state,
                isCompleted = item.isCompleted ?: false
            ) {}
        }
    }

}