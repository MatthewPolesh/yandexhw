package com.example.todo.UIComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ToDoItem

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
                colors = CheckboxDefaults.colors(checkedColor = colorResource(id = R.color.green)),
                onCheckedChange = { onClick() },
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.description,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(280.dp)
                        .padding(end = 15.dp)

                )
                if (item.deadline !="")
                    Text(
                        text = item.deadline
                    )
            }

        }
    }
}