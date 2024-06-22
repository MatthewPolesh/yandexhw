package com.example.todo.UIComponents

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.ToDoItem
import com.example.todo.UIComponent.MyDatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMenu(
    item: ToDoItem,
    OnDismissRequest: () -> Unit,
    OnSaveRequest: (ToDoItem) -> Unit
) {
    val newText = remember { mutableStateOf(item.description) }
    var checked by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<String?>(item.deadline) }
    var selectedImportance by remember { mutableStateOf(item.importance) }



    var dateText = ""
    if (selectedDate != null && selectedDate != "") {
       dateText = "${selectedDate.toString()}"
    }
    else
        dateText = ""

    if (showDatePicker)
    {
        MyDatePicker(
            onDismissRequest = {showDatePicker = false},
            onDateSelected = {
                selectedDate = it.toString()
                showDatePicker = false
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    modifier = Modifier.clickable { OnDismissRequest() }
                    )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Сохранить",
                    color = colorResource(id = R.color.blue),
                    modifier = Modifier.clickable { val newItem =
                        selectedDate?.let { item.copy(description = newText.value, deadline = it, importance = selectedImportance) }; if (newItem != null) {
                        OnSaveRequest(newItem)
                        }
                    }
                )
            }
            Box(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                TextField(
                    value = newText.value,
                    onValueChange = { newText.value = it },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .height(150.dp),
                    colors = TextFieldDefaults.textFieldColors(

                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,)
                )
            }
            Box(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                PopUpMenu(){
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
                        Text(text = "Сделать до")
                        Text(text = dateText,
                            color = colorResource(id = R.color.blue),
                            fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = checked,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor =  colorResource(id = R.color.blue),


                        ),
                        onCheckedChange = { checked = it; if (selectedDate == null) showDatePicker = !showDatePicker else {selectedDate = null;showDatePicker = !showDatePicker} })
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = null
                    )
                    Text(
                        text = "Удалить",
                        modifier = Modifier.clickable { OnDismissRequest() }
                        )
                }

            }
        }
    }
}