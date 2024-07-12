package com.example.todo.UIComponents

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ToDoItem
import com.example.todo.UIComponent.MyDatePicker
import com.example.todo.UIComponents.Theme.AppTheme
import com.example.todo.UIComponents.Theme.Blue
import com.example.todo.UIComponents.Theme.DarkBlue
import com.example.todo.UIComponents.Theme.Gray
import com.example.todo.UIComponents.Theme.Red
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.absoluteValue
import kotlin.random.Random

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMenu(
    item: ToDoItem,
    onDismissRequest: () -> Unit,
    onSaveRequest: (ToDoItem) -> Unit,
    onDeleteRequest: (ToDoItem) -> Unit,
    onUpdateRequest: (ToDoItem) -> Unit
) {

    var description by remember { mutableStateOf(item.description) }
    var deadLine by remember { mutableStateOf(item.deadline) }
    var selectedImportance by remember { mutableStateOf(item.importance) }
    var checked by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")


    var dateText = ""
    if (deadLine != null) {
        dateText = Instant.ofEpochMilli(deadLine!!).atZone(ZoneId.systemDefault()).format(dateFormatter)
    } else{
        dateText = ""
    }


    if (showDatePicker) {
        MyDatePicker(
            onDismissRequest = {
                showDatePicker = false
                checked = false
            },
            onDateSelected = {
                deadLine = it.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
                showDatePicker = false
                checked = true
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                    modifier = Modifier.clickable { onDismissRequest() }
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Сохранить",
                    color = Blue,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable {
                        if (item.id != "0")
                        {
                            var newItem = item.copy(
                                id = item.id,
                                importance = selectedImportance,
                                completed = false,
                                description = description,
                                deadline = deadLine,
                                )
                            onUpdateRequest(newItem)
                        } else {
                            var newItem = item.copy(
                                id = ThreadLocalRandom.current().nextInt().toString(),
                                importance = selectedImportance,
                                completed = false,
                                description = description,
                                deadline = deadLine,
                            )
                            onSaveRequest(newItem)
                        }
                    }
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .shadow(5.dp, RoundedCornerShape(10.dp))
            ) {
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.do_smth),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    },

                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(150.dp),
                    colors = TextFieldDefaults.textFieldColors(

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = MaterialTheme.colorScheme.secondary,

                        )
                )
            }
            Box(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                PopUpMenu() {
                    selectedImportance = it
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                    ) {
                        Text(
                            text = stringResource(id = R.string.complete),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,

                            )
                        if (dateText != "")
                            Text(
                                text = dateText,
                                color = Blue,
                                style = MaterialTheme.typography.bodySmall
                            )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = checked,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Blue,
                            checkedTrackColor = DarkBlue,
                            uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                            uncheckedTrackColor = Gray,
                            uncheckedBorderColor = Color.Transparent
                        ),
                        onCheckedChange = {
                            checked = it
                            if (deadLine == null){
                                showDatePicker = !showDatePicker
                            } else {
                                deadLine = null
                                showDatePicker = !showDatePicker
                            }
                        })
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
            Box(
                modifier = Modifier.clickable { onDeleteRequest(item) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        tint = Red,
                        modifier = Modifier.padding(end = 10.dp),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(id = R.string.delete),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Red,
                    )
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewEditMenuDark() {
    val item = ToDoItem("0", "0", false, "", 0, 0, 0, "1")
    AppTheme {
        EditMenu(
            item,
            onDismissRequest = {},
            onDeleteRequest = {},
            onSaveRequest = {},
            onUpdateRequest = {}
        )
    }
}

@Preview()
@Composable
fun PreviewEditMenuLight() {
    val item = ToDoItem("0", "0", false, "", 0, 0, 0,"1")
    AppTheme {
        EditMenu(
            item,
            onDismissRequest = {},
            onDeleteRequest = {},
            onSaveRequest = {},
            onUpdateRequest = {}
        )
    }
}