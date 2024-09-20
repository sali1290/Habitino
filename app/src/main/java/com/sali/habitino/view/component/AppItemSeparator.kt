package com.sali.habitino.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppItemSeparator() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .height(2.dp)
    )
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
    )
}