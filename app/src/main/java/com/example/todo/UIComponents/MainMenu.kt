package com.example.todo.UIComponents

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ToDoItem
import com.example.todo.TodoItemsRepository


@SuppressLint("StateFlowValueCalledInComposition", "SuspiciousIndentation")
@Composable
fun MainMenu(
    repository: TodoItemsRepository
) {
    val visibility = repository.visibleCompleted.collectAsState()
    val itemsList = repository.itemsList.collectAsState()
    val listState = rememberLazyListState()
    var showEditMenu by remember { mutableStateOf(false)}
    val basicItem = ToDoItem("0",0,false,"","","","")
    var selectedItem by remember { mutableStateOf(basicItem) }



    if (showEditMenu)
    {
        EditMenu(item = selectedItem , OnDismissRequest = {showEditMenu = !showEditMenu; repository.deleteItem(selectedItem);selectedItem = basicItem}) {
            repository.saveNewItem(it)
            showEditMenu = !showEditMenu
        }
    }
    else
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.Start


        ) {

            Header(
                listState = listState,
                repository
            )
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(20.dp))
                        .clip(shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(5.dp)

                ) {
                    items(itemsList.value) { item ->
                        if (!(item.completed and visibility.value))
                            DismissSwiper(item = item, repository = repository){ showEditMenu = !showEditMenu; selectedItem = item}



                    }
                    item {
                        Box(
                            modifier = Modifier
                                .clickable { showEditMenu = !showEditMenu }
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
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
                                    text = "Новое",
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
                onClick = { showEditMenu = !showEditMenu },
                shape = CircleShape,

                containerColor =  colorResource(id = R.color.blue),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.add), contentDescription = null)
            }
        }

    }
}