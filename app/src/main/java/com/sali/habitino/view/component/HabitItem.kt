package com.sali.habitino.view.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sali.habitino.R

@Composable
fun HabitItem(
    selfAdded: Boolean = false,
    onDeleteClick: () -> Unit = {},
    title: String,
    description: String,
    solution: String? = null,
    state: String,
    isCompleted: Boolean,
    onCompleteClick: () -> Unit,
) {
    var showDetail by remember { mutableStateOf(false) }
    var cardHeight by remember { mutableStateOf(0.dp) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(bottom = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .height(cardHeight)
                .weight(0.15f)
                .background(
                    color = if (state == stringResource(R.string.good)) Color.Green else Color.Red,
                    shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onCompleteClick) {
                Icon(
                    imageVector = if (isCompleted)
                        Icons.Default.Check
                    else
                        Icons.Default.Clear,
                    contentDescription = stringResource(R.string.is_it_completed_or_not)
                )
            }
        }

        val density = LocalDensity.current
        Card(
            modifier = Modifier
                .weight(0.7f)
                .clickable { showDetail = !showDetail }
                .onGloballyPositioned { coordinates ->
                    cardHeight = with(density) { coordinates.size.height.toDp() }
                },
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            shape = RectangleShape
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = title, fontSize = 18.sp)
                AnimatedVisibility(visible = showDetail) {
                    Column {
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(text = description, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(25.dp))
                        if (!solution.isNullOrEmpty()) {
                            Text(text = stringResource(R.string.possible_solution), fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = solution, fontSize = 16.sp)
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .height(cardHeight)
                .weight(0.15f)
                .background(
                    color = CardDefaults.cardColors().containerColor,
                    shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selfAdded) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_habit)
                    )
                }
            }
        }

    }
}
