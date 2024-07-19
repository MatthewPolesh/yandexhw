package com.example.todo.presentation.uicomponents.divkit

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.example.todo.R
import com.yandex.div.core.font.DivTypefaceProvider
import javax.inject.Inject

class MyTypefaceProvider @Inject constructor(
    private val context: Context
) : DivTypefaceProvider {

    override fun getRegular(): Typeface {
        return ResourcesCompat.getFont(context, R.font.roboto_regular) ?: Typeface.DEFAULT
    }

    override fun getMedium(): Typeface {
        return ResourcesCompat.getFont(context, R.font.roboto_regular) ?: Typeface.DEFAULT
    }

    override fun getLight(): Typeface {
        return ResourcesCompat.getFont(context, R.font.roboto_light) ?: Typeface.DEFAULT
    }

    override fun getBold(): Typeface {
        return ResourcesCompat.getFont(context, R.font.roboto_bold) ?: Typeface.DEFAULT
    }
}