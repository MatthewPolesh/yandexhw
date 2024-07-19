package com.example.todo.presentation.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.domain.ThemeSettings
import com.example.todo.presentation.uicomponents.theme.AppTheme
import com.example.todo.presentation.uicomponents.theme.Blue
import com.example.todo.presentation.uicomponents.theme.White


@Composable
fun SettingsMenu(
    selectedSetting: ThemeSettings,
    onThemeClickRequest: (ThemeSettings) -> Unit,
    onAppClickRequest: () -> Unit,
    onSaveRequest: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onSaveRequest() },
                shape = CircleShape,
                containerColor = Blue,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    tint = White,
                    contentDescription = null
                )
            }
        },
        topBar = {
            Column(modifier = Modifier.padding(top = 40.dp, start = 40.dp)) {
                Text(
                    text = stringResource(id = R.string.settings),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,

                    )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onAppClickRequest() },
                ) {
                    Text(
                        text = stringResource(id = R.string.about_app),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.info),
                        tint = MaterialTheme.colorScheme.onTertiary,
                        contentDescription = null,
                        modifier = Modifier
                            .scale(0.9f)
                            .padding(start = 3.dp)
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {}
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp)
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                SettingsItem(
                    selectedSetting = selectedSetting,
                    title = stringResource(id = R.string.theme),
                    settings = listOf(
                        ThemeSettings.SYSTEM,
                        ThemeSettings.LIGHT,
                        ThemeSettings.DARK
                    ),
                    onClickRequest = { setting -> onThemeClickRequest(setting) })
            }
        }
    }
}


@Preview()
@Composable
fun PreviewSettingsMenuDark() {
    AppTheme(ThemeSettings.DARK) {
        SettingsMenu(
            selectedSetting = ThemeSettings.DARK,
            onThemeClickRequest = {},
            onAppClickRequest = {},
            onSaveRequest = {})
    }

}

@Preview()
@Composable
fun PreviewSettingsMenuLight() {
    AppTheme(ThemeSettings.LIGHT) {
        SettingsMenu(
            selectedSetting = ThemeSettings.LIGHT,
            onThemeClickRequest = {},
            onAppClickRequest = {},
            onSaveRequest = {}
        )
    }

}