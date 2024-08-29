package com.sali.habitino.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PermissionDialog(
    isShown: MutableState<Boolean>,
    title: String,
    backgroundColor: Color = Color.LightGray,
    rejectText: String = "Reject",
    acceptText: String = "Accept",
    onRejectClick: () -> Unit,
    onAcceptClick: () -> Unit
) {
    if (isShown.value)
        Dialog(onDismissRequest = {
            onRejectClick.invoke()
            isShown.value = !isShown.value
        }) {

            Column(
                modifier = Modifier
                    .background(color = backgroundColor, shape = RoundedCornerShape(10.dp))
                    .padding(15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(modifier = Modifier.fillMaxWidth(), text = title)

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onRejectClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Text(text = rejectText)
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(onClick = onAcceptClick) {
                        Text(text = acceptText)
                    }
                }
            }

        }

}