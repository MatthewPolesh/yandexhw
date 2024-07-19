package com.example.todo.presentation.uicomponents

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.ThemeSettings
import com.example.todo.domain.ToDoItem
import com.example.todo.presentation.uicomponents.theme.AppTheme
import com.example.todo.presentation.uicomponents.theme.Blue
import com.example.todo.presentation.uicomponents.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainMenu(
    counter: State<Int>,
    itemsList: State<List<ToDoItem>>,
    onCreateRequest: () -> Unit,
    onAcceptRequest: (ToDoItem) -> Unit,
    onDeleteRequest: (ToDoItem) -> Unit,
    onClickInfoRequest: (ToDoItem) -> Unit,
    onClickCheckboxRequest: (ToDoItem) -> Unit,
    onSettingsRequest: () -> Unit
) {
    var visibility by remember { mutableStateOf(false) }
    val itemsList = itemsList
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { ErrorSnackbar(snackbarData = it) })
        },
        topBar = {
            Header(
                listState = listState,
                counter = counter.value,
                onVisibilityRequest = { visibility = it },
                onSettingsRequest = { onSettingsRequest() },
                visibility = visibility
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreateRequest() },
                shape = CircleShape,
                containerColor = Blue,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    tint = White,
                    contentDescription = null
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
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
                        if (!(item.completed and visibility)) {
                            val deleteFlag = true
                            val completeFlag = item.completed
                            val canceled = stringResource(id = R.string.canceled)
                            val deleted = stringResource(id = R.string.delete_item)
                            DismissSwiper(
                                item = item,
                                deleteFlag = deleteFlag,
                                completeFlag = completeFlag,
                                onAcceptRequest = { onAcceptRequest(item) },
                                onDeleteRequest = {
                                    scope.launch {
                                        withContext(Dispatchers.Main) {
                                            val result = snackbarHostState
                                                .showSnackbar(
                                                    message = deleted + " \"${item.description}\"",
                                                    actionLabel = canceled,
                                                    duration = SnackbarDuration.Short,
                                                )
                                            if (result != SnackbarResult.ActionPerformed) {
                                                onDeleteRequest(item)
                                            }
                                        }
                                    }


                                },
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

        }
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewMainMenuDark() {
    AppTheme(ThemeSettings.DARK) {
        val counter = MutableStateFlow(0).collectAsState()
        val list = MutableStateFlow(emptyList<ToDoItem>()).collectAsState()
        MainMenu(
            counter,
            list,
            onSettingsRequest = {},
            onCreateRequest = {},
            onClickInfoRequest = {},
            onClickCheckboxRequest = {},
            onAcceptRequest = {},
            onDeleteRequest = {}

        )
    }
}

@Preview()
@Composable
fun PreviewMainMenuLight() {
    AppTheme(ThemeSettings.LIGHT) {
        val counter = MutableStateFlow(0).collectAsState()
        val list = MutableStateFlow(emptyList<ToDoItem>()).collectAsState()
        MainMenu(
            counter,
            list,
            onSettingsRequest = {},
            onCreateRequest = {},
            onClickInfoRequest = {},
            onClickCheckboxRequest = {},
            onAcceptRequest = {},
            onDeleteRequest = {}

        )
    }
}