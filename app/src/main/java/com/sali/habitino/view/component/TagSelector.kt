package com.sali.habitino.view.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sali.habitino.R

@Composable
fun TagSelector(tagsList: SnapshotStateList<String> = mutableStateListOf("")) {

    var tagsIsEnabled by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(0.15f)
                .background(
                    color = if (tagsIsEnabled) CardDefaults.cardColors().containerColor else Color.Transparent,
                    shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = { tagsIsEnabled = true }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search by tag")
            }
        }

        Box(modifier = Modifier.weight(0.85f)) {
            this@Row.AnimatedVisibility(
                visible = tagsIsEnabled,
                enter = expandHorizontally(animationSpec = tween(500)),
                exit = shrinkHorizontally(animationSpec = tween(100)),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(
                        color = CardDefaults.cardColors().containerColor,
                        shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
                    )
                ) {
                    Text(
                        text = tagsList.toList().toString()
                            .substring(1, tagsList.toList().toString().length - 1),
                        modifier = Modifier
                            .weight(0.85f)
                            .horizontalScroll(rememberScrollState())
                    )
                    Box(
                        modifier = Modifier
                            .weight(0.15f)
                            .background(
                                color = CardDefaults.cardColors().containerColor,
                                shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { tagsIsEnabled = false }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.delete_all_tags)
                            )
                        }
                    }
                }
            }
        }
    }

}