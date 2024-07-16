package com.sali.habitino.view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sali.habitino.R
import com.sali.habitino.viewmodel.RemoteHabitViewModel
import java.time.LocalDateTime

@Composable
fun RemoteHabitsList(onCompletedClick: (Int) -> Unit) {

    val remoteHabitViewModel: RemoteHabitViewModel = hiltViewModel()
    val remoteHabitsState by remoteHabitViewModel.habits.collectAsState()
    LaunchedEffect(key1 = Unit) {
        remoteHabitViewModel.getAllHabits()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) { innerPadding ->

        when {
            remoteHabitsState.loading -> {
                ProgressBar()
            }

            remoteHabitsState.result.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    itemsIndexed(remoteHabitsState.result) { _, item ->
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

            !remoteHabitsState.error.isNullOrEmpty() -> {
                ErrorMessage(message = remoteHabitsState.error!!) { remoteHabitViewModel.getAllHabits() }
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