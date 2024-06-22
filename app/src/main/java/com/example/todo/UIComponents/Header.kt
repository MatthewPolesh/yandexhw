package com.example.todo.UIComponents

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.TodoItemsRepository
import java.util.concurrent.CountDownLatch
import kotlin.math.min


@Composable
fun Header(
    listState: LazyListState,
    repository: TodoItemsRepository
) {
    val icon = repository.visibleCompleted.collectAsState()
    val counter = repository.completedItemsCount.collectAsState()
    val scrollOffset by remember {
        derivedStateOf {
            min(
                1f,
                1 - (listState.firstVisibleItemScrollOffset / 600f + listState.firstVisibleItemIndex).coerceAtLeast(
                    0f
                )
            )
        }
    }
    var iconRes = painterResource(id = R.drawable.visibility)
    when{
        icon.value -> iconRes = painterResource(id = R.drawable.visibility)
        !icon.value -> iconRes = painterResource(id = R.drawable.visibility_off)
    }

    val animatedHeaderHeight by animateDpAsState(
        targetValue = (100.dp * scrollOffset).coerceAtLeast(
            56.dp
        )
    )
    val animatedFontSize1 by animateFloatAsState(
        targetValue = (20.sp * scrollOffset).value.coerceAtLeast(
            16.sp.value
        )
    )
    val animatedFontSize2 by animateFloatAsState(
        targetValue = (32.sp * scrollOffset).value.coerceAtLeast(
            16.sp.value
        )
    )

    val (text, visible) = when {
        animatedHeaderHeight > 60.dp -> Pair("Выполнено - ${counter.value}", 1f)
        else -> Pair("Мои дела", 0f)
    }

    Box(
        modifier = Modifier
            .padding(
                top = (40.dp * scrollOffset).coerceAtLeast(5.dp),
                start = (40.dp * scrollOffset).coerceAtLeast(10.dp),
            )
            .fillMaxWidth()
            .height(animatedHeaderHeight),
        contentAlignment = Alignment.Center
    )
    {
        Column {
            Text(
                text = "Мои дела",
                fontSize = animatedFontSize2.sp,
                modifier = Modifier.alpha(visible)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontSize = animatedFontSize1.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = iconRes,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .padding(end = 10.dp)
                        .clickable { repository.changeVisibility()}
                )
            }

        }

    }

}