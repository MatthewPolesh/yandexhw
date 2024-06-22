package com.example.todo.UIComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = null)
                DatePicker(state = datePickerState)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "ОТМЕНА")
                    }
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000)))
                        }
                    }) {
                        Text(text = "ГОТОВО")
                    }
                }
            }
        }
    }
}