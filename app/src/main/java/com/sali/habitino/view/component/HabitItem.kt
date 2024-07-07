package com.sali.habitino.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HabitItem(modifier: Modifier, title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        shape = RoundedCornerShape(size = 15.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = modifier.padding(vertical = 20.dp, horizontal = 15.dp)
        )
    }
}