package com.example.todo.UIComponents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ToDoItem
import com.example.todo.TodoItemsRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissSwiper(
    item: ToDoItem,
    repository: TodoItemsRepository,
    OnClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    val (icon, alignment) = when (dismissState.targetValue) {
        SwipeToDismissBoxValue.StartToEnd -> Pair(
            R.drawable.check,
            Alignment.CenterStart
        )
        SwipeToDismissBoxValue.EndToStart -> Pair(
            R.drawable.delete,
            Alignment.CenterEnd
        )
        else -> Pair(R.drawable.info, Alignment.CenterEnd)
    }

    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.StartToEnd -> repository.completeItem(item)

        SwipeToDismissBoxValue.EndToStart -> repository.deleteItem(item)

        else -> Pair(R.drawable.info, Alignment.CenterEnd)
    }

    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            SwipeToDismissBoxValue.EndToStart -> colorResource(id = R.color.red)
            SwipeToDismissBoxValue.StartToEnd -> colorResource(id = R.color.green)
            else -> MaterialTheme.colorScheme.surface
        }, label = ""
    )
    val scale by animateFloatAsState(
        if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 1f else 1.25f,
        label = ""
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(color)
                    .fillMaxSize(),
                contentAlignment = alignment,
            )
            {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .scale(scale)
                        .padding(vertical = 5.dp)
                        .clickable { OnClick() }
                )
            }
        },
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = true,
        content = { Item(item, onClick = {
            repository.changeItem(item)
            repository.changeCounter(item)
        }) }

    )
}
