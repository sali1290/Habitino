package com.sali.habitino.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sali.habitino.R
import com.sali.habitino.view.theme.LightBlue

@Composable
fun SaveMessageDialog(
    initialMessage: String = "",
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit
) {

    var message by remember { mutableStateOf(initialMessage) }
    Dialog(onDismissRequest = onCancel) {
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
                value = message,
                onValueChange = { message = it },
                placeholder = { Text(text = stringResource(R.string.add_note_placeholder)) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontSize = 20.sp,
                    color = Color.Red,
                    modifier = Modifier.clickable { onCancel() })
                Text(
                    text = stringResource(R.string.confirm),
                    fontSize = 20.sp,
                    color = LightBlue,
                    modifier = Modifier.clickable {
                        onConfirm(message)
                    })
            }
        }
    }

}