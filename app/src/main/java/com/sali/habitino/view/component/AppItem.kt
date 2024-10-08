package com.sali.habitino.view.component

import android.graphics.drawable.Drawable
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.sali.habitino.R

@Composable
fun AppItem(
    icon: Drawable,
    name: String,
    message: String = "",
    initialCheck: Boolean,
    onEditClick: () -> Unit = {},
    onCheckClick: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(0.7f), verticalAlignment = Alignment.CenterVertically) {

            Canvas(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
            ) {
                drawImage(
                    image = icon.toBitmap(size.width.toInt(), size.height.toInt()).asImageBitmap()
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = name)
            }
        }

        Spacer(modifier = Modifier.width(5.dp))

        var isChecked by remember { mutableStateOf(initialCheck) }
        Row(
            modifier = Modifier.weight(0.3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (message.isNotEmpty()) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_message),
                    )
                }
            }
            IconButton(
                onClick = {
                    isChecked = !isChecked
                    onCheckClick.invoke(isChecked)
                },
                modifier = Modifier
                    .border(width = 1.dp, shape = CircleShape, color = Color.LightGray)
            ) {
                Crossfade(targetState = isChecked, label = "Cross fade animation") { isChecked ->
                    if (isChecked) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.add_a_note_to_the_app),
                            tint = Color.Blue
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.remove_app_s_note)
                        )
                    }
                }
            }
        }
    }

}