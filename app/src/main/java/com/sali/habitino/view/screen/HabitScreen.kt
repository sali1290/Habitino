package com.sali.habitino.view.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HabitScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    title: String, description: String,
    state: String, isDone: Boolean
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = title,
            modifier = Modifier.sharedElement(
                state = rememberSharedContentState(key = "title"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = { _, _ ->
                    tween(durationMillis = 1000)
                }
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = description)

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = state)

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = isDone.toString())
    }

}