package com.example.todo.presentation.uicomponents

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import card
import com.example.todo.R
import com.example.todo.domain.ThemeSettings
import com.example.todo.presentation.uicomponents.divkit.MyTypefaceProvider
import com.example.todo.presentation.uicomponents.divkit.NavigationDivActionHandler
import com.example.todo.presentation.uicomponents.theme.AppTheme
import com.example.todo.presentation.uicomponents.theme.Blue
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.glide.GlideDivImageLoader
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div2.DivData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.math.roundToInt

fun JSONObject.asDiv2Data(): DivData {
    val card = getJSONObject("card")
    val environment = DivParsingEnvironment(ParsingErrorLogger.LOG)
    return DivData(environment, card)
}

fun Color.toHex(): String {
    return String.format(
        "#%02X%02X%02X",
        (red * 255).roundToInt(),
        (green * 255).roundToInt(),
        (blue * 255).roundToInt()
    )
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AboutMenu(
    activity: Activity,
    onClick: () -> Unit
) {
    val imageLoader = GlideDivImageLoader(activity)
    val navDivActionHandler = NavigationDivActionHandler()
    val configuration = DivConfiguration
        .Builder(imageLoader)
        .typefaceProvider(MyTypefaceProvider(activity))
        .actionHandler(navDivActionHandler)
        .build()
    val divData = JSONObject(card).asDiv2Data()
    val div2View = Div2View(Div2Context(activity, configuration = configuration))
    div2View.setData(divData, DivDataTag("MyDivView"))
    val bgColor = MaterialTheme.colorScheme.background.toHex()
    val onPrimary = MaterialTheme.colorScheme.onPrimary.toHex()
    val secondary = MaterialTheme.colorScheme.secondary.toHex()
    val onSecondary = MaterialTheme.colorScheme.onSecondary.toHex()
    val blue = Blue.toHex()
    div2View.setVariable("about_app", stringResource(id = R.string.about_app))
    div2View.setVariable("text_app", stringResource(id = R.string.about_text))
    div2View.setVariable("developer", stringResource(id = R.string.developer))
    div2View.setVariable("dev_name", stringResource(id = R.string.dev_name))
    div2View.setVariable("bgColor", bgColor)
    div2View.setVariable("onPrimary", onPrimary)
    div2View.setVariable("secondary", secondary)
    div2View.setVariable("onSecondary", onSecondary)
    div2View.setVariable("blue", blue)
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main) {
            navDivActionHandler.isClicked.collectLatest { isClicked ->
                if (isClicked) {
                    onClick()
                }
            }
        }
    }
    AndroidView(
        factory = { div2View },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun PreviewAboutMenuDark(){
    AppTheme(themeSettings = ThemeSettings.DARK) {
        AboutMenu(activity = Activity()) {}
    }
}
@Preview
@Composable
fun PreviewAboutMenuLight(){
    AppTheme(themeSettings = ThemeSettings.LIGHT) {
        AboutMenu(activity = Activity()) {}
    }
}

