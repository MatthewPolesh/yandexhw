package com.example.todo.presentation.uicomponents

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.ThemeSettings
import com.example.todo.presentation.uicomponents.theme.AppTheme
import com.example.todo.presentation.uicomponents.theme.Green

@Composable
fun SettingsItem(
    selectedSetting: ThemeSettings,
    title: String,
    settings: List<ThemeSettings>,
    onClickRequest: (ThemeSettings) -> Unit
) {
    var selected by remember { mutableStateOf(selectedSetting) }

    Column(
        modifier = Modifier
            .padding(top = 12.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(start = 15.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        for (item in settings) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = (selected == item),
                    onCheckedChange = { selected = item; onClickRequest(selected) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Green,
                        uncheckedColor = MaterialTheme.colorScheme.onTertiary
                    ),
                )
                Text(
                    text = when(item.text){
                                          "system" -> stringResource(id = R.string.theme_system)
                                          "dark" -> stringResource(id = R.string.theme_dark)
                                          "light" -> stringResource(id = R.string.theme_light)
                        else -> {stringResource(id = R.string.theme_system)}
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview()
@Composable
fun PreviewSettingsItemDark() {
    AppTheme(ThemeSettings.DARK) {
        SettingsItem(
            ThemeSettings.DARK,
            "Тема",
            listOf(ThemeSettings.SYSTEM, ThemeSettings.LIGHT, ThemeSettings.DARK),
            onClickRequest = {})
    }
}

@Preview()
@Composable
fun PreviewSettingsItemLight() {
    AppTheme(ThemeSettings.LIGHT) {
        SettingsItem(
            ThemeSettings.LIGHT,
            "Тема",
            listOf(ThemeSettings.SYSTEM, ThemeSettings.LIGHT, ThemeSettings.DARK),
            onClickRequest = {})
    }
}