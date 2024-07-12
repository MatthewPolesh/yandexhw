package com.example.todo.UIComponents

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.MainViewModel
import com.example.todo.UIComponents.Theme.AppTheme
import com.example.todo.UIComponents.Theme.Blue


@Composable
fun ErrorDialog(
    showDialog: MutableState<Boolean>,
    viewModel: MainViewModel
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
                (if (viewModel.error.value != null) viewModel.error.value else "Неопознанная ошибка")?.let {
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
                    viewModel.updateError("Nothing")
                    showDialog.value = !showDialog.value

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