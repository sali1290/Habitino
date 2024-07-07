package com.sali.habitino.view.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sali.habitino.view.component.HabitItem
import com.sali.habitino.view.component.HabitMenuItem

@Composable
fun MainHabitScreen() {

    var badHabitListEnabled by remember { mutableStateOf(true) }
    var goodHabitListEnabled by remember { mutableStateOf(false) }
    var selfAddedHabitListEnabled by remember { mutableStateOf(false) }

//    var habitList = remember { mutableStateListOf() }

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
            items(3) {
                HabitItem(title = "Habit $it")
            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun MainHabitScreenPreview() {
    MainHabitScreen()
}