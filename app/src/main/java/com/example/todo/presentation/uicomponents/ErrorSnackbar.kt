package com.example.todo.presentation.uicomponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.todo.presentation.uicomponents.theme.Red

@Composable
fun ErrorSnackbar(
    snackbarData: SnackbarData
) {
    Snackbar(
        snackbarData = snackbarData,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        actionColor = Red,
        actionContentColor = Red,
        dismissActionContentColor = MaterialTheme.colorScheme.onSecondary,
        shape = RoundedCornerShape(20.dp)
    )
}
