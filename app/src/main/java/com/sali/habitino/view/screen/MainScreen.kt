package com.sali.habitino.view.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sali.habitino.model.dto.SelfAddedHabit
import com.sali.habitino.view.component.HabitItem
import com.sali.habitino.view.component.HabitMenuItem
import com.sali.habitino.viewmodel.SelfAddedHabitViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MainHabitScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onHabitItemClickListener: (
        title: String, description: String,
        state: String, isDone: Boolean
    ) -> Unit
) {
    val selfAddedHabitViewModel: SelfAddedHabitViewModel = hiltViewModel()

    var badHabitListEnabled by remember { mutableStateOf(true) }
    var goodHabitListEnabled by remember { mutableStateOf(false) }
    var selfAddedHabitListEnabled by remember { mutableStateOf(false) }

    val selfAddedHabitsState = selfAddedHabitViewModel.selfAddedHabits.collectAsState()
    LaunchedEffect(key1 = Unit) {
        selfAddedHabitViewModel.getAllSelfAddedHabits()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HabitMenuItem(
                modifier = Modifier.weight(0.33f),
                text = "Bad habits",
                enabled = badHabitListEnabled,
                enabledColor = Color.Red
            ) {
                badHabitListEnabled = true
                goodHabitListEnabled = false
                selfAddedHabitListEnabled = false
            }

            HabitMenuItem(
                modifier = Modifier.weight(0.33f),
                text = "Good habits",
                enabled = goodHabitListEnabled,
                enabledColor = Color.Green
            ) {
                badHabitListEnabled = false
                goodHabitListEnabled = true
                selfAddedHabitListEnabled = false
            }

            HabitMenuItem(
                modifier = Modifier.weight(0.33f),
                text = "Self added",
                enabled = selfAddedHabitListEnabled,
                enabledColor = Color.DarkGray
            ) {
                badHabitListEnabled = false
                goodHabitListEnabled = false
                selfAddedHabitListEnabled = true
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            if (selfAddedHabitListEnabled && selfAddedHabitsState.value.isNotEmpty()) {
                itemsIndexed(selfAddedHabitsState.value) { _, item ->
                    HabitItem(
                        modifier = Modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = item.id),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 1000)
                                }
                            )
                            .clickable {
                                onHabitItemClickListener(
                                    item.title,
                                    item.description,
                                    item.state,
                                    item.isCompleted ?: false
                                )
                            },
                        title = item.title
                    )
                }
            } else {
                items(3) {
                    HabitItem(
                        modifier = Modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = "Habit $it"),
                                animatedVisibilityScope = animatedVisibilityScope,
                                boundsTransform = { _, _ ->
                                    tween(durationMillis = 1000)
                                }
                            )
                            .clickable {
                                onHabitItemClickListener(
                                    "Habit $it",
                                    " ",
                                    " ",
                                    true
                                )
                            },
                        title = "Habit $it"
                    )
                }
            }
        }

    }

}