package com.sali.habitino.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sali.habitino.R
import com.sali.habitino.viewmodel.main.MainActions
import com.sali.habitino.viewmodel.main.MainScreenState
import java.time.LocalDateTime

@Composable
fun CommonHabitsList(
    screenState: MainScreenState,
    onAction: (MainActions) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onAction(MainActions.GetCommonHabits)
    }
    Scaffold(
        contentWindowInsets = WindowInsets(left = 10.dp, top = 0.dp, right = 10.dp, bottom = 0.dp)
    ) { innerPadding ->

        when {
            screenState.loading -> ProgressBar()

            !screenState.commonHabits.isNullOrEmpty() -> {
                HabitList(
                    screenState = screenState,
                    onAction = onAction,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            screenState.commonHabits == null -> {
                ErrorMessage(message = stringResource(id = R.string.something_went_wrong_please_try_again)) {
                    onAction(MainActions.GetCommonHabits)
                }
            }

            !screenState.error.isNullOrEmpty() -> {
                ErrorMessage(message = screenState.error) {
                    onAction(MainActions.GetCommonHabits)
                }
            }
        }

    }
}

@Composable
private fun HabitList(
    screenState: MainScreenState,
    onAction: (MainActions) -> Unit,
    modifier: Modifier
) {
    val tagsList = remember { mutableStateListOf<String>() }
    Column(modifier = Modifier.fillMaxSize()) {
        TagSelector(tagsList = tagsList)
        LazyColumn(modifier = modifier) {
            itemsIndexed(screenState.commonHabits ?: emptyList()) { _, item ->
                if (item.tags.names.containsAll(tagsList))
                    HabitItem(
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
                            MainActions.UpdateCommonHabit(
                                score = screenState.score + newScore,
                                commonHabit = updatedItem
                            )
                        )
                    }
            }
        }
    }
}

@Composable
private fun ErrorMessage(message: String, onTryAgain: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment =
            Alignment.CenterHorizontally
        ) {
            Text(text = message, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = onTryAgain) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}

@Composable
private fun ProgressBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}