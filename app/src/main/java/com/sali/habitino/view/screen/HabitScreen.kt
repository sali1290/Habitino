package com.sali.habitino.view.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HabitScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    title: String, description: String,
    state: String, isDone: Boolean
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Green, shape = RoundedCornerShape(size = 15.dp))
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = title),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 1000)
                    }
                )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = description, modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Green, shape = RoundedCornerShape(size = 15.dp))
                .padding(horizontal = 10.dp, vertical = 20.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = state, modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Green, shape = RoundedCornerShape(size = 15.dp))
                .padding(horizontal = 10.dp, vertical = 20.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = isDone.toString())
    }

}