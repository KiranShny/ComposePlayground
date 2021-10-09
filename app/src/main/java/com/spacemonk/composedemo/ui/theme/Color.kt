package com.spacemonk.composedemo.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color as ComposeColor
import android.graphics.Color as AndroidColor

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


fun ComposeColor.Companion.parse(colorString: String): ComposeColor =
    ComposeColor(color = AndroidColor.parseColor(colorString))