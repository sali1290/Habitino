package com.sali.habitino.view.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sali.habitino.view.component.HabitTypeItem
import com.sali.habitino.view.component.RemoteHabitsList
import com.sali.habitino.view.component.SelfAddedHabitsList
import com.sali.habitino.view.theme.LightBlue
import com.sali.habitino.viewmodel.ScoreStateViewModel

@Composable
fun MainHabitScreen() {
    var habitListEnabled by remember { mutableIntStateOf(0) }

    val scoreStateViewModel: ScoreStateViewModel = hiltViewModel()
    val scoreState by scoreStateViewModel.score.collectAsState()
    LaunchedEffect(key1 = Unit) {
        scoreStateViewModel.getScore()
    }
    var score by remember { mutableIntStateOf(0) }
    LaunchedEffect(key1 = scoreState) {
        score = scoreState
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HabitTypeItem(
                modifier = Modifier.weight(0.33f),
                text = "Common habits",
                enabled = habitListEnabled == 0
            ) {
                habitListEnabled = 0
            }

            HabitTypeItem(
                modifier = Modifier.weight(0.33f),
                text = "Self added",
                enabled = habitListEnabled == 1
            ) {
                habitListEnabled = 1
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Your score: $score",
            fontSize = 20.sp,
            color = LightBlue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            textAlign = TextAlign.Start
        )

        AnimatedContent(
            targetState = habitListEnabled,
            label = "Habit lists",
            transitionSpec = {
                if (targetState == 0) {
                    slideInHorizontally(
                        animationSpec = tween(500),
                        initialOffsetX = { fullWidth -> -fullWidth }
                    ) togetherWith slideOutHorizontally(
                        animationSpec = tween(500),
                        targetOffsetX = { fullWidth -> fullWidth }
                    )
                } else {
                    slideInHorizontally(
                        animationSpec = tween(500),
                        initialOffsetX = { fullWidth -> fullWidth }
                    ) togetherWith slideOutHorizontally(
                        animationSpec = tween(500),
                        targetOffsetX = { fullWidth -> -fullWidth }
                    )
                }
            }
        ) { targetState ->
            when (targetState) {
                0 -> {
                    RemoteHabitsList {
                        score += it
                        scoreStateViewModel.saveScore(score)
                    }
                }

                1 -> {
                    SelfAddedHabitsList {
                        score += it
                        scoreStateViewModel.saveScore(score)
                    }
                }
            }
        }
    }

}