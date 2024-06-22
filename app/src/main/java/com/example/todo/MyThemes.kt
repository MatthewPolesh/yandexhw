package com.example.todo

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource

@SuppressLint("ResourceAsColor")
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            darkColorScheme(
                primary = Color(R.color.primary_dark),
                secondary = Color(R.color.secondary_dark),
                tertiary = Color( R.color.tertiary_dark),


                )
        } else {
            lightColorScheme(
                primary = colorResource(id = R.color.primary_light),
                secondary = colorResource(id = R.color.secondary_light),


            )
        },
        content = content,
    )
}
