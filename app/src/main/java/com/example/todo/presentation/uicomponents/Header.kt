package com.example.todo.presentation.uicomponents

import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.R
import com.example.todo.domain.ThemeSettings
import com.example.todo.presentation.MainViewModel
import com.example.todo.presentation.uicomponents.theme.AppTheme
import com.example.todo.presentation.uicomponents.theme.Blue
import kotlin.math.min


@Composable
fun Header(
    listState: LazyListState,
    counter: Int,
    visibility: Boolean,
    onVisibilityRequest: (Boolean) -> Unit,
    onSettingsRequest:() -> Unit
) {
    var icon = visibility
    val totalScrollOffset by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset + listState.firstVisibleItemIndex * 500
        }
    }
    val scrollOffset = min(1f, 1 - (totalScrollOffset / 600f).coerceAtLeast(0f))

    var iconRes = painterResource(id = R.drawable.visibility_off)
    when {
        icon -> iconRes = painterResource(id = R.drawable.visibility_off)
        !icon -> iconRes = painterResource(id = R.drawable.visibility)
    }

    val animatedHeaderHeight by animateDpAsState(
        targetValue = (100.dp * scrollOffset).coerceAtLeast(
            56.dp
        )
    )
    val animatedFontSize1 by animateFloatAsState(
        targetValue = (MaterialTheme.typography.titleMedium.fontSize * scrollOffset).value.coerceAtLeast(
            16.sp.value
        )
    )
    val animatedFontSize2 by animateFloatAsState(
        targetValue = (MaterialTheme.typography.titleLarge.fontSize * scrollOffset).value.coerceAtLeast(
            16.sp.value
        )
    )

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(bottom = (15.dp * scrollOffset).coerceAtLeast(0.dp))
            .fillMaxWidth()
            .height(animatedHeaderHeight),
        contentAlignment = Alignment.Center
    )
    {
        Box(
            modifier = Modifier
                .padding(
                    top = (40.dp * scrollOffset).coerceAtLeast(5.dp),
                    start = (40.dp * scrollOffset).coerceAtLeast(10.dp),
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.my_tasks),
                        fontSize = animatedFontSize2.sp,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    if (animatedHeaderHeight > 60.dp)
                        Text(
                            text = stringResource(id = R.string.done_tasks) + " - ${counter}",
                            fontSize = animatedFontSize1.sp,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                }

                Spacer(modifier = Modifier.weight(1f))
                Row {
                    Icon(
                        painter = iconRes,
                        tint = Blue,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 23.dp)
                            .clip(shape = CircleShape)
                            .clickable {
                                icon = !icon
                                onVisibilityRequest(icon)
                            }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.settings),
                        tint = Blue,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 23.dp)
                            .clip(shape = CircleShape)
                            .clickable {
                                onSettingsRequest()
                            }
                    )
                }

            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHeaderDark() {
    AppTheme(ThemeSettings.DARK) {
        Header(
            rememberLazyListState(),
            0,
            false,
            onVisibilityRequest = {},
            onSettingsRequest = {}
        )
    }
}

@Preview()
@Composable
fun PreviewHeaderLight() {
    AppTheme(ThemeSettings.LIGHT) {
        Header(
            rememberLazyListState(),
            0,
            false,
            onVisibilityRequest = {},
            onSettingsRequest = {}
        )
    }
}


