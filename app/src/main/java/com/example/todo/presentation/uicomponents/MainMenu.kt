package com.example.todo.presentation.uicomponents

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.ToDoItem
import com.example.todo.presentation.uicomponents.theme.Blue
import com.example.todo.presentation.uicomponents.theme.White


@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@Composable
fun MainMenu(
    counter: State<Int>,
    itemsList: State<List<ToDoItem>>,
    onCreateRequest: () -> Unit,
    onAcceptRequest: (ToDoItem) -> Unit,
    onDeleteRequest: (ToDoItem) -> Unit,
    onClickInfoRequest: (ToDoItem) -> Unit,
    onClickCheckboxRequest: (ToDoItem) -> Unit
) {
    var visibility by remember { mutableStateOf(false) }
    val itemsList = itemsList
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {

            Header(
                listState = listState,
                counter = counter.value,
                onVisibilityRequest = { visibility = it },
                visibility = visibility
            )
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .shadow(2.dp, shape = RoundedCornerShape(20.dp))
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxWidth()
                    .wrapContentHeight()


            ) {
                items(itemsList.value) { item ->
                    if (!(item.completed and visibility))
                    {
                        val deleteFlag = true
                        val completeFlag = item.completed
                        DismissSwiper(
                            item = item,
                            deleteFlag = deleteFlag,
                            completeFlag = completeFlag,
                            onAcceptRequest =  { onAcceptRequest(item) } ,
                            onDeleteRequest = { onDeleteRequest(item) },
                            onClickCheckboxRequest = { onClickCheckboxRequest(item) },
                            onClickInfoRequest = { onClickInfoRequest(item) }
                    )
                    }
                }
                item {
                    Box(modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondary)
                        .clickable {
                            onCreateRequest()
                        }
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start

                        ) {
                            Checkbox(
                                checked = true,
                                onCheckedChange = {},
                                modifier = Modifier.alpha(0f)
                            )
                            Text(
                                text = stringResource(id = R.string.new_item),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier
                                    .width(280.dp)
                                    .padding(end = 15.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.info),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .alpha(0f)
                            )
                        }

                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                onCreateRequest()
            },
            shape = CircleShape,
            containerColor = Blue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp)
                .padding(horizontal = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add),
                tint = White,
                contentDescription = null
            )
        }
    }
}


//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun PreviewMainMenuDark() {
//    val viewModel = MainViewModel()
//    AppTheme {
//        MainMenu(
//            viewModel = viewModel,
//            null
//        )
//    }
//}
//
//@Preview()
//@Composable
//fun PreviewMainMenuLight() {
//    val viewModel = MainViewModel()
//    AppTheme {
//        MainMenu(
//            viewModel = viewModel,
//            null
//        )
//    }
//}