package com.example.todo.presentation.uicomponents

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.todo.domain.Importance
import com.example.todo.presentation.uicomponents.theme.AppTheme
import com.example.todo.presentation.uicomponents.theme.Red

@Composable
fun PopUpMenu(
    onClick: (String) -> Unit,
    importance: String
) {
    val list = listOf("low", "basic", "important")
    val expanded = remember { mutableStateOf(false) }
    val currentValue = remember { mutableStateOf(importance) }

    Column(modifier = Modifier.clickable { expanded.value = !expanded.value }) {
        Text(
            text = "Важность",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            text = when(currentValue.value){
                "low" -> "Низкая"
                "basic" -> "Средняя"
                "important" -> "!!Срочная"
                else -> "Низкая"},
            style = MaterialTheme.typography.bodySmall,
            color = if (currentValue.value == "important") {
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
                            onClick(item)
                            expanded.value = false
                            currentValue.value = item
                        }, text = {
                            Text(
                                text = when(item){
                                    "low" -> "Низкая"
                                    "basic" -> "Средняя"
                                    "important" -> "!!Срочная"
                                    else -> {"Низкая"}
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (item == "!!Срочная") {
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
        PopUpMenu(importance = "basic", onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPopUpMenuLight() {
    AppTheme {
        PopUpMenu(importance = "basic", onClick = {})
    }
}
