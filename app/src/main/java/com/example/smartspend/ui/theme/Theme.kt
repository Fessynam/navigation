package com.example.smartspend.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
@Composable
fun SmartSpendTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = DarkPrimary,
            secondary = DarkSecondary,
            tertiary = DarkAccent,
            background = DarkBackground,
            surface = DarkSurface,
            onPrimary = DarkOnPrimary,
            onSecondary = DarkOnSecondary,
            onTertiary = DarkOnAccent,
            onBackground = DarkOnBackground,
            onSurface = DarkOnSurface
        )
        else -> lightColorScheme(
            primary = LightPrimary,
            secondary = LightSecondary,
            tertiary = LightAccent,
            background = LightBackground,
            surface = LightSurface,
            onPrimary = LightOnPrimary,
            onSecondary = LightOnSecondary,
            onTertiary = LightOnAccent,
            onBackground = LightOnBackground,
            onSurface = LightOnSurface
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}