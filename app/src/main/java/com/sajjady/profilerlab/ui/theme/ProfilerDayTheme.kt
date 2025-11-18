package com.sajjady.profilerlab.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DayColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC5),
    onSecondary = Color.Black,
    tertiary = Color(0xFF3700B3),
    background = Color(0xFFFDFCFF),
    onBackground = Color(0xFF1B1B1F),
    surface = Color(0xFFFFFBFF),
    onSurface = Color(0xFF1B1B1F)
)

@Composable
fun ProfilerDayTheme(content: @Composable () -> Unit) {
    // Locks the entire UI to a light color scheme regardless of system setting.
    MaterialTheme(
        colorScheme = DayColorScheme,
        content = content
    )
}
