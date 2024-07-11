package com.sali.habitino.view.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sali.habitino.view.component.HabitTypeItem
import com.sali.habitino.view.component.RemoteHabitsList
import com.sali.habitino.view.component.SelfAddedHabitsList
import com.sali.habitino.view.theme.LightBlue

@Composable
fun MainHabitScreen() {
    var habitListEnabled by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
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
                text = "Bad habits",
                enabled = habitListEnabled == 0,
                enabledColor = Color.Red
            ) {
                habitListEnabled = 0
            }

            HabitTypeItem(
                modifier = Modifier.weight(0.33f),
                text = "Good habits",
                enabled = habitListEnabled == 1,
                enabledColor = Color.Green
            ) {
                habitListEnabled = 1
            }

            HabitTypeItem(
                modifier = Modifier.weight(0.33f),
                text = "Self added",
                enabled = habitListEnabled == 2,
                enabledColor = Color.DarkGray
            ) {
                habitListEnabled = 2
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Your score: $score",
            fontSize = 20.sp,
            color = LightBlue,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(15.dp))

        when (habitListEnabled) {
            0 -> {
                RemoteHabitsList()
            }

            1 -> {
                RemoteHabitsList()
            }

            2 -> {
                SelfAddedHabitsList {
                    score += it
                }
            }
        }

    }

}