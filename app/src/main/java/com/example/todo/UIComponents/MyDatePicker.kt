package com.example.todo.UIComponent

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.todo.UIComponents.Theme.AppTheme
import com.example.todo.UIComponents.Theme.Blue
import com.example.todo.UIComponents.Theme.White
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
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(5.dp)
        ) {
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = null)
                DatePicker(
                    state = datePickerState,
                    title = null,
                    colors = DatePickerColors(
                        headlineContentColor = MaterialTheme.colorScheme.onPrimary,
                        selectedDayContentColor = White,
                        selectedYearContainerColor = Blue,
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = White,
                        weekdayContentColor = MaterialTheme.colorScheme.onTertiary,
                        subheadContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationContentColor = MaterialTheme.colorScheme.onPrimary,
                        yearContentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledYearContentColor = MaterialTheme.colorScheme.onTertiary,
                        currentYearContentColor = MaterialTheme.colorScheme.onPrimary,
                        selectedDayContainerColor = Blue,
                        selectedYearContentColor = White,
                        disabledDayContentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledSelectedYearContentColor = MaterialTheme.colorScheme.onTertiary,
                        disabledSelectedYearContainerColor = Color.Transparent,
                        dayContentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledSelectedDayContainerColor = Color.Transparent,
                        disabledSelectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                        todayContentColor = Blue,
                        todayDateBorderColor = Color.Transparent,
                        dayInSelectionRangeContainerColor = Color.Transparent,
                        dayInSelectionRangeContentColor = Color.Transparent,
                        dividerColor = MaterialTheme.colorScheme.outline,
                        dateTextFieldColors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                )


                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(
                            text = "ОТМЕНА",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Blue
                        )
                    }
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000)))
                        }
                    }) {
                        Text(
                            text = "ГОТОВО",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Blue
                        )
                    }
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMyDatePickerDark() {
    AppTheme {
        MyDatePicker(
            onDismissRequest = { Unit }
        ) {
        }
    }
}

@Preview()
@Composable
fun PreviewMyDatePickerLight() {
    AppTheme {
        MyDatePicker(
            onDismissRequest = { Unit }
        ) {
        }
    }
}