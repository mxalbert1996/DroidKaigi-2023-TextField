package com.mxalbert.compose.textfield.ui

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val LightThemeColors = lightColorScheme()
private val DarkThemeColors = darkColorScheme()

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private val useDynamicTheme = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = when {
            useDynamicTheme -> {
                val context = LocalContext.current
                if (darkTheme) {
                    dynamicDarkColorScheme(context)
                } else {
                    dynamicLightColorScheme(context)
                }
            }
            else -> if (darkTheme) DarkThemeColors else LightThemeColors
        },
        typography = Typography
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides
                    OverscrollConfiguration(glowColor = MaterialTheme.colorScheme.primary),
            content = content
        )
    }
}
