package com.sali.habitino.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sali.habitino.R
import com.sali.habitino.viewmodel.main.MainActions
import com.sali.habitino.viewmodel.main.MainScreenState
import java.time.LocalDateTime

@Composable
fun SelfAddedHabitsList(screenState: MainScreenState, onAction: (MainActions) -> Unit) {

    var showAddHabitDialog by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        onAction(MainActions.GetSelfAddedHabits)
    }
    if (showAddHabitDialog) {
        AddHabitDialog(
            onDismissRequest = {
                showAddHabitDialog = false
            },
            onConfirmRequest = { title, description, solution, state, tags ->
                onAction(
                    MainActions.AddHabit(
                        title,
                        description,
                        solution,
                        state,
                        tags
                    )
                )
                showAddHabitDialog = false
            }
        )
    }
    Scaffold(
        floatingActionButton = {
            AddHabitFloatingActionButton {
                showAddHabitDialog = true
            }
        },
        contentWindowInsets = WindowInsets(left = 10.dp, top = 0.dp, right = 10.dp, bottom = 0.dp)
    ) { innerPadding ->
        HabitList(
            screenState = screenState,
            onAction = onAction,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HabitList(
    screenState: MainScreenState, onAction: (MainActions) -> Unit, modifier: Modifier
) {
    val tagsList = remember { mutableStateListOf<String>() }
    Column(modifier = Modifier.fillMaxSize()) {
        TagSelector(tagsList = tagsList)
        LazyColumn(modifier = modifier) {
            itemsIndexed(screenState.selfAddedHabits ?: emptyList()) { _, item ->
                HabitItem(
                    selfAdded = true,
                    onDeleteClick = { onAction(MainActions.DeleteHabit(item)) },
                    title = item.title,
                    description = item.description,
                    solution = item.solution,
                    state = item.state,
                    tags = item.tags.names,
                    isCompleted = item.isCompleted
                ) {
                    val updatedItem = item.copy(
                        isCompleted = !item.isCompleted,
                        lastCompletedDate = LocalDateTime.now()
                    )
                    val newScore = if (!item.isCompleted) 1 else -1
                    onAction(
                        MainActions.UpdateSelfAddedHabit(
                            score = screenState.score + newScore,
                            selfAddedHabit = updatedItem
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun AddHabitFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, stringResource(R.string.add_habit))
    }
}