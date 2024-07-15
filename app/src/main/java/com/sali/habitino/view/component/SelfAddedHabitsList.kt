package com.sali.habitino.view.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sali.habitino.R
import com.sali.habitino.viewmodel.SelfAddedHabitViewModel
import java.time.LocalDateTime

@Composable
fun SelfAddedHabitsList(onCompletedClick: (Int) -> Unit) {

    val selfAddedHabitViewModel: SelfAddedHabitViewModel = hiltViewModel()
    val selfAddedHabitsState by selfAddedHabitViewModel.habits.collectAsState()
    LaunchedEffect(key1 = Unit) {
        selfAddedHabitViewModel.getAllSelfAddedHabits()
    }

    var showAddHabitDialog by remember { mutableStateOf(false) }

    if (showAddHabitDialog) {
        AddHabitDialog(
            onDismissRequest = {
                showAddHabitDialog = false
            },
            onConfirmRequest = { title, description, solution, state ->
                selfAddedHabitViewModel.insertSelfAddedHabit(
                    title = title, description = description, solution = solution, state = state
                )
                showAddHabitDialog = false
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        floatingActionButton = {
            AddHabitFloatingActionButton {
                showAddHabitDialog = true
            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            itemsIndexed(selfAddedHabitsState) { _, item ->
                HabitItem(
                    selfAdded = true,
                    onDeleteClick = { selfAddedHabitViewModel.deleteSelfAddedHabit(item) },
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

                    selfAddedHabitViewModel.updateHabit(updatedItem)
                }
            }
        }

    }

}

@Composable
fun AddHabitFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, stringResource(R.string.add_habit))
    }
}