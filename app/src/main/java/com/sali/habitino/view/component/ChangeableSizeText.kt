package com.sali.habitino.view.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ChangeableSizeText(text: String) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(text) }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                finalText = text
            }

            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(0)
                val showMoreString = "..."
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                finalText = "$adjustedText$showMoreString"

                isClickable = true
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = TextFieldDefaults.MinHeight)
            .padding(8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = finalText,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            onTextLayout = { textLayoutResultState.value = it },
            textAlign = TextAlign.Start,
            modifier = Modifier
                .clickable(enabled = isClickable) { isExpanded = !isExpanded }
                .animateContentSize(),
        )
    }
}
