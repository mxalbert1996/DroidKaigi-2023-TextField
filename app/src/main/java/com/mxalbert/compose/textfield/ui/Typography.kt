package com.mxalbert.compose.textfield.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

// Always use the default values before Compose 1.6
private val DefaultPlatformTextStyle = PlatformTextStyle(includeFontPadding = true)
private val DefaultLineHeightStyle = LineHeightStyle.Default

val Typography = Typography().run {
    Typography(
        displayLarge = displayLarge.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        displayMedium = displayMedium.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        displaySmall = displaySmall.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        headlineLarge = headlineLarge.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        headlineMedium = headlineMedium.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        headlineSmall = headlineSmall.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        titleLarge = titleLarge.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        titleMedium = titleMedium.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        titleSmall = titleSmall.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        bodyLarge = bodyLarge.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        bodyMedium = bodyMedium.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        bodySmall = bodySmall.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        labelLarge = labelLarge.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        labelMedium = labelMedium.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        ),
        labelSmall = labelSmall.copy(
            platformStyle = DefaultPlatformTextStyle,
            lineHeightStyle = DefaultLineHeightStyle
        )
    )
}

val TextFieldTextStyle = TextStyle(
    platformStyle = PlatformTextStyle(includeFontPadding = false),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Proportional,
        trim = LineHeightStyle.Trim.None
    ),
    fontSize = 16.sp,
    lineHeight = 24.sp
)
