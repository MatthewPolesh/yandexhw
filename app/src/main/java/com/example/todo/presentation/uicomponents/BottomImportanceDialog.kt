package com.example.todo.presentation.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.domain.ThemeSettings
import com.example.todo.presentation.uicomponents.theme.AppTheme
import com.example.todo.presentation.uicomponents.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomImportanceDialog(
    onClickRequest:(String) -> Unit,
    onDismissRequest:() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        containerColor = MaterialTheme.colorScheme.secondary,
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp , start = 10.dp, end = 10.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Выберите важность",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary
                )
            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
            Text(
                text = "Низкая",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .clip(shape = CircleShape)
                    .padding(3.dp)
                    .clickable { onClickRequest("low") }
            )
            Text(
                text = "Средняя",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .clip(shape = CircleShape)
                    .padding(3.dp)
                    .clickable { onClickRequest("basic") }

            )
            Text(
                text = "!!Срочная",
                style = MaterialTheme.typography.titleSmall.copy(color = Red),
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .clip(shape = CircleShape)
                    .padding(3.dp)
                    .clickable { onClickRequest("important") }
            )
        }
    }
}
