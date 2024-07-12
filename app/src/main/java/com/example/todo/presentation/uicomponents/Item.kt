package com.example.todo.presentation.uicomponents

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
import com.example.todo.domain.ToDoItem
import com.example.todo.presentation.uicomponents.theme.AppTheme
import com.example.todo.presentation.uicomponents.theme.Blue
import com.example.todo.presentation.uicomponents.theme.Green
import com.example.todo.presentation.uicomponents.theme.Red
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun Item(
    item: ToDoItem,
    onClick: () -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

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
                    checkedColor = Green, uncheckedColor = if (item.importance == "important") Red else MaterialTheme.colorScheme.onTertiary
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
                        text = Instant.ofEpochMilli(item.deadline!!).atZone(ZoneId.systemDefault()).format(dateFormatter),
                        style = MaterialTheme.typography.bodySmall, color = Blue
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