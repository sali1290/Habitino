package com.sali.habitino.view.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RemoteHabitsList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(3) {
            HabitItem(
                title = "Habit $it",
                description = "Description $it",
                solution = "Solution $it",
                state = "State $it",
                isCompleted = false
            ) {}
        }
    }

}