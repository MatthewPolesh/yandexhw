package com.example.todo.UIComponents

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ToDoItem
import com.example.todo.UIComponents.Theme.AppTheme
import com.example.todo.UIComponents.Theme.Blue
import com.example.todo.UIComponents.Theme.Green

@Composable
fun Item(
    item: ToDoItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Checkbox(
                checked = item.completed,
                colors = CheckboxDefaults.colors(
                    checkedColor = Green,
                    uncheckedColor = MaterialTheme.colorScheme.onTertiary
                ),
                onCheckedChange = { onClick() },
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(

                    text = item.description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = if (item.completed) {
                        MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough)
                    } else {
                        MaterialTheme.typography.bodyMedium
                    },
                    color = if (item.completed) {
                        MaterialTheme.colorScheme.onTertiary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                    modifier = Modifier
                        .width(280.dp)
                        .padding(end = 15.dp, bottom = 3.dp)

                )
                if (item.deadline != null && !item.completed)
                    Text(
                        text = item.deadline!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = Blue
                    )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewItemDark() {
    AppTheme {
        Item(
            previewToDoItem,
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemLight() {
    AppTheme {
        Item(
            previewToDoItem,
        ) {}
    }
}