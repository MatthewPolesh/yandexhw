package com.example.todo.UIComponents

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.Importance
import com.example.todo.MainViewModel
import com.example.todo.R
import com.example.todo.ToDoItem
import com.example.todo.UIComponents.Theme.AppTheme
import com.example.todo.UIComponents.Theme.Green
import com.example.todo.UIComponents.Theme.Red

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissSwiper(
    item: ToDoItem,
    viewModel: MainViewModel,
    OnClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    val deletedFlag = remember { mutableStateOf(false) }
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
        SwipeToDismissBoxValue.StartToEnd -> {
            viewModel.completeItem(item); }

        SwipeToDismissBoxValue.EndToStart -> {
            viewModel.deleteItem(item); deletedFlag.value = !deletedFlag.value
        }

        else -> Pair(R.drawable.info, Alignment.CenterEnd)
    }

    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            SwipeToDismissBoxValue.EndToStart -> Red
            SwipeToDismissBoxValue.StartToEnd -> Green
            else -> MaterialTheme.colorScheme.secondary
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
                    tint = MaterialTheme.colorScheme.onTertiary,
                    contentDescription = null,
                    modifier = Modifier
                        .scale(scale)
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 5.dp)
                        .clickable { OnClick() }
                )
            }
        },
        enableDismissFromEndToStart = viewModel.itemsList.value.contains(item),
        enableDismissFromStartToEnd = !item.completed,
        content = {
            Item(item, onClick = {
                viewModel.changeItem(item)
                viewModel.checkCompleted()
            })
        }

    )
}


val previewToDoItem =
    ToDoItem("1", Importance.Low.description, true, "Мое дело 1", null, null, null)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDismissSwiperDark() {
    val viewModel = MainViewModel()
    AppTheme {
        DismissSwiper(
            item = previewToDoItem,
            viewModel = viewModel,
            OnClick = {}
        )
    }
}

@Preview()
@Composable
fun PreviewDismissSwiperLight() {
    val viewModel = MainViewModel()
    AppTheme {
        DismissSwiper(
            item = previewToDoItem,
            viewModel = viewModel,
            OnClick = {}
        )
    }
}
