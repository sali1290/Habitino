package com.sali.habitino.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@Composable
fun MultiChoiceDropDownMenu(
    expanded: MutableState<Boolean>,
    value: String = "",
    listItems: List<String> = listOf(
        "foods and drinks",
        "productivity",
    ),
    onMenuItemClick: (String, Int) -> Unit = { _, _ -> }
) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = {
            expanded.value = false
        }
    ) {
        listItems.forEachIndexed { itemIndex, itemValue ->
            var isChecked by remember { mutableStateOf(false) }
            isChecked = value.contains(itemValue)
            DropdownMenuItem(
                onClick = {
                    isChecked = !isChecked
                    onMenuItemClick(itemValue, itemIndex)
                },
                text = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = !isChecked
                                onMenuItemClick(itemValue, itemIndex)
                            }
                        )
                        Text(text = itemValue)
                    }
                },
                enabled = true
            )
        }
    }
}