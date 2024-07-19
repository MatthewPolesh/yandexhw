package com.example.todo.presentation.uicomponents

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.ToDoItem
import com.example.todo.presentation.uicomponents.theme.Green
import com.example.todo.presentation.uicomponents.theme.Red

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissSwiper(
    item: ToDoItem,
    deleteFlag: Boolean,
    completeFlag: Boolean,
    onClickInfoRequest: () -> Unit,
    onAcceptRequest: () -> Unit,
    onDeleteRequest: @Composable () -> Unit,
    onClickCheckboxRequest: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState()
    var dFlag = deleteFlag
    var cFlag = completeFlag

    val (icon, alignment) = when (dismissState.targetValue) {
        SwipeToDismissBoxValue.StartToEnd -> Pair(
            R.drawable.check, Alignment.CenterStart
        )
        SwipeToDismissBoxValue.EndToStart -> Pair(
            R.drawable.delete, Alignment.CenterEnd
        )
        else -> Pair(R.drawable.info, Alignment.CenterEnd)
    }

    when (dismissState.currentValue) {
        SwipeToDismissBoxValue.StartToEnd -> {
            cFlag = !cFlag
            onAcceptRequest()
        }

        SwipeToDismissBoxValue.EndToStart -> {
            dFlag = !dFlag
            onDeleteRequest()
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
        if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 1f else 1.25f, label = ""
    )

    SwipeToDismissBox(state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(color)
                    .fillMaxSize(),
                contentAlignment = alignment,
            ) {
                Icon(painter = painterResource(id = icon),
                    tint = MaterialTheme.colorScheme.onTertiary,
                    contentDescription = null,
                    modifier = Modifier
                        .scale(scale)
                        .padding(horizontal = 10.dp)
                        .padding(vertical = 5.dp)
                        .padding(5.dp)
                        .clickable {
                            onClickInfoRequest();
                        }
                )
            }
        },
        enableDismissFromEndToStart = dFlag,
        enableDismissFromStartToEnd = !cFlag,
        content = {
            Item(item, onClick ={
                cFlag = !cFlag
                onClickCheckboxRequest()
            }
            )
        }

    )
}


val previewToDoItem =
    ToDoItem("1", "!!Срочная", false, "Мое дело 1", 0, 0, null, "1")

//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewDismissSwiperDark() {
//    val viewModel = MainViewModel()
//    AppTheme {
//        DismissSwiper(item = previewToDoItem, viewModel = viewModel, onClick = {})
//    }
//}
//
//@Preview()
//@Composable
//fun PreviewDismissSwiperLight() {
//    val viewModel = MainViewModel()
//    AppTheme {
//        DismissSwiper(item = previewToDoItem, viewModel = viewModel, onClick = {})
//    }
//}
