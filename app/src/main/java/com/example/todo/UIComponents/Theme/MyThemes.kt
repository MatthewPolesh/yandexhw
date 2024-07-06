package com.example.todo.UIComponents.Theme

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val DarkColorScheme = darkColorScheme(
    primary = BackPrimaryDark,
    secondary = BackSecondaryDark,
    onPrimary = LabelPrimaryDark,
    onSecondary = LabelSecondaryDark,
    surface = BackElevatedDark,
    tertiary = BackElevatedDark,
    onTertiary = LabelTertiaryDark,
    background = BackPrimaryDark,
    onSurface = SupOverlayDark,
    outline = SupSeparatorDark
)

val LightColorScheme = lightColorScheme(
    primary = BackPrimaryLight,
    secondary = BackSecondaryLight,
    onPrimary = LabelPrimaryLight,
    onSecondary = LabelSecondaryLight,
    surface = BackElevatedLight,
    tertiary = BackElevatedLight,
    onTertiary = LabelTertiaryLight,
    background = BackPrimaryLight,
    onSurface = SupOverlayLight,
    outline = SupSeparatorLight
)


@SuppressLint("ResourceAsColor")
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            DarkColorScheme
        } else {
            LightColorScheme
        },
        typography = AppTypography,
        content = content,
    )
}

@Preview()
@Composable
fun PreviewAppThemeLight() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Primary Back", color = Color.Black)
                    }
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondary)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Secondary Back", color = Color.Black)
                    }
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Elevated Back", color = Color.Black)
                    }
                }
                Row {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Primary Label", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onSecondary)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Secondary Label", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onBackground)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Tertiary Label", color = Color.White)
                    }
                }

                Row {
                    Box(
                        modifier = Modifier
                            .background(Blue)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Blue", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(Red)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Red", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(Green)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Green", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(Gray)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Gray", color = Color.White)
                    }

                }
                Box(
                    modifier = Modifier
                        .background(GrayLight)
                        .size(100.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(text = "Light Gray", color = Color.Black)
                }
            }

        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppThemeDark() {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Primary Back", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondary)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Secondary Back", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Elevated Back", color = Color.White)
                    }
                }
                Row {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Primary Label", color = Color.Black)
                    }
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onSecondary)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Secondary Label", color = Color.Black)
                    }
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onBackground)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Tertiary Label", color = Color.Black)
                    }
                }

                Row {
                    Box(
                        modifier = Modifier
                            .background(Blue)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Blue", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(Red)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Red", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(Green)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Green", color = Color.White)
                    }
                    Box(
                        modifier = Modifier
                            .background(Gray)
                            .size(100.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(text = "Gray", color = Color.White)
                    }

                }
                Box(
                    modifier = Modifier
                        .background(GrayLight)
                        .size(100.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(text = "Light Gray", color = Color.Black)
                }
            }

        }
    }

}
