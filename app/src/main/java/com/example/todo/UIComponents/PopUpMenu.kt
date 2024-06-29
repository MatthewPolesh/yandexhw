package com.example.todo.UIComponents

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.todo.R
import com.example.todo.UIComponents.Theme.AppTheme
import com.example.todo.UIComponents.Theme.Red

@Composable
fun PopUpMenu(
    OnClick: (Int) -> Unit
) {
    val list = listOf(R.string.imp_low, R.string.imp_medium, R.string.imp_high)
    val expanded = remember { mutableStateOf(false) }
    val current_value = remember { mutableStateOf(list[0]) }

    Column(modifier = Modifier.clickable { expanded.value = !expanded.value }) {
        Text(
            text = "Важность",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            text = stringResource(id = current_value.value),
            style = MaterialTheme.typography.bodySmall,
            color = if (current_value.value == R.string.imp_high) {
                Red
            } else {
                MaterialTheme.colorScheme.onTertiary
            }
        )
    }
    if (expanded.value) {
        Popup(
            onDismissRequest = { expanded.value = !expanded.value },
        ) {
            Surface(
                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp, topEnd = 20.dp),
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .width(167.5.dp)
                    .animateContentSize()
            ) {
                Column {
                    list.forEach { item ->
                        DropdownMenuItem(onClick = {
                            OnClick(item)
                            expanded.value = false
                            current_value.value = item
                        },
                            text = {
                                Text(
                                    text = stringResource(id = item),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (item == R.string.imp_high) {
                                        Red
                                    } else {
                                        MaterialTheme.colorScheme.onPrimary
                                    }
                                )
                            })
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewPopUpMenuDark() {
    AppTheme {
        PopUpMenu() {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPopUpMenuLight() {
    AppTheme {
        PopUpMenu() {
        }
    }
}
