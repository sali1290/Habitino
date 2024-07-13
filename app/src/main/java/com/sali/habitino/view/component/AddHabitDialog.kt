package com.sali.habitino.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sali.habitino.R
import com.sali.habitino.view.theme.LightBlue

@Composable
fun AddHabitDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: (String, String, String, Boolean) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var solution by remember { mutableStateOf("") }
    var state by remember { mutableStateOf(true) }

    Dialog(onDismissRequest = onDismissRequest) {

        Column(
            modifier = Modifier
                .background(
                    color = CardDefaults.cardColors().containerColor,
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .padding(horizontal = 10.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text(text = "Title*") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text(text = "Description*") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = solution,
                onValueChange = { solution = it },
                placeholder = { Text(text = "Solution") }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .widthIn(120.dp)
                        .background(
                            color = if (state) Color.Green else Color.Gray,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .padding(10.dp)
                        .clickable { state = true }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_happy),
                        contentDescription = "Good habit",
                        modifier = Modifier.weight(0.8f)
                    )
                    Text(text = "Good habit", modifier = Modifier.weight(0.2f))
                }

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .widthIn(120.dp)
                        .background(
                            color = if (!state) Color.Red else Color.Gray,
                            shape = RoundedCornerShape(size = 10.dp)
                        )
                        .padding(10.dp)
                        .clickable { state = false })
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sad),
                        contentDescription = "Bad habit",
                        modifier = Modifier.weight(0.8f)
                    )
                    Text(text = "Bad habit", modifier = Modifier.weight(0.2f))
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Decline",
                    fontSize = 20.sp,
                    color = Color.Red,
                    modifier = Modifier.clickable { onDismissRequest() })
                Text(
                    text = "Confirm",
                    fontSize = 20.sp,
                    color = LightBlue,
                    modifier = Modifier.clickable {
                        if (title.isNotEmpty() && description.isNotEmpty())
                            onConfirmRequest(
                                title,
                                description,
                                solution,
                                state
                            )
                    })
            }
        }

    }

}