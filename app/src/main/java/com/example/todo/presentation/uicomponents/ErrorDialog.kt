package com.example.todo.presentation.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.todo.presentation.uicomponents.theme.Blue


@Composable
fun ErrorDialog(
    showDialog: MutableState<Boolean>,
    error: String?,
    onErrorAccept: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primary,
        title = {
            Text(
                text = "Информация об ошибке",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        text = {
            Box {
                (error ?: "Неопознанная ошибка")?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        onDismissRequest = { showDialog.value = !showDialog.value },
        dismissButton = {
            Text(
                text = "ОK",
                style = MaterialTheme.typography.bodyLarge,
                color = Blue,
                modifier = Modifier.clickable {
                    onErrorAccept()


                }
            )
        },
        confirmButton = { /*TODO*/ }
    )
}

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewErrorDialogDark() {
//    val showDialog = remember { mutableStateOf(false) }
//    val viewModel = MainViewModel()
//    AppTheme {
//        ErrorDialog(showDialog = showDialog, viewModel = viewModel)
//    }
//}
//
//@Preview()
//@Composable
//fun PreviewErrorDialogLight() {
//    val showDialog = remember { mutableStateOf(false) }
//    val viewModel = MainViewModel()
//    AppTheme {
//        ErrorDialog(showDialog = showDialog, viewModel = viewModel)
//    }
//}