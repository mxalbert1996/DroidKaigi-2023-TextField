package com.mxalbert.compose.textfield.ui.demo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp

object CustomTextFieldDefaults {

    @Composable
    fun colors(
        textColor: Color = MaterialTheme.colorScheme.onSurface,
        disabledTextColor: Color = textColor.copy(alpha = 0.38f),
        errorTextColor: Color = MaterialTheme.colorScheme.error,
        cursorColor: Color = MaterialTheme.colorScheme.primary,
        errorCursorColor: Color = MaterialTheme.colorScheme.error,
        focusedIndicatorColor: Color = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        errorIndicatorColor: Color = MaterialTheme.colorScheme.error,
        focusedLabelColor: Color = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLabelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        errorLabelColor: Color = MaterialTheme.colorScheme.error,
        placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        disabledPlaceholderColor: Color = placeholderColor
    ): CustomTextFieldColors = CustomTextFieldColors(
        textColor = textColor,
        disabledTextColor = disabledTextColor,
        errorTextColor = errorTextColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        focusedIndicatorColor = focusedIndicatorColor,
        unfocusedIndicatorColor = unfocusedIndicatorColor,
        disabledIndicatorColor = disabledIndicatorColor,
        errorIndicatorColor = errorIndicatorColor,
        focusedLabelColor = focusedLabelColor,
        unfocusedLabelColor = unfocusedLabelColor,
        disabledLabelColor = disabledLabelColor,
        errorLabelColor = errorLabelColor,
        placeholderColor = placeholderColor,
        disabledPlaceholderColor = disabledPlaceholderColor
    )
}

@Immutable
data class CustomTextFieldColors(
    val textColor: Color,
    val disabledTextColor: Color,
    val errorTextColor: Color,
    val cursorColor: Color,
    val errorCursorColor: Color,
    val focusedIndicatorColor: Color,
    val unfocusedIndicatorColor: Color,
    val disabledIndicatorColor: Color,
    val errorIndicatorColor: Color,
    val focusedLabelColor: Color,
    val unfocusedLabelColor: Color,
    val disabledLabelColor: Color,
    val errorLabelColor: Color,
    val placeholderColor: Color,
    val disabledPlaceholderColor: Color
) {
    @Composable
    fun indicatorColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledIndicatorColor
            isError -> errorIndicatorColor
            focused -> focusedIndicatorColor
            else -> unfocusedIndicatorColor
        }
        return if (enabled) {
            @Suppress("AnimateAsStateLabel")
            animateColorAsState(targetValue, tween(durationMillis = AnimationDuration))
        } else {
            rememberUpdatedState(targetValue)
        }
    }

    @Composable
    fun placeholderColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) placeholderColor else disabledPlaceholderColor)
    }

    @Composable
    fun labelColor(
        enabled: Boolean,
        error: Boolean,
        interactionSource: InteractionSource
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()

        val targetValue = when {
            !enabled -> disabledLabelColor
            error -> errorLabelColor
            focused -> focusedLabelColor
            else -> unfocusedLabelColor
        }
        return rememberUpdatedState(targetValue)
    }

    @Composable
    fun textColor(enabled: Boolean, isError: Boolean): State<Color> {
        return rememberUpdatedState(
            when {
                !enabled -> disabledTextColor
                isError -> errorTextColor
                else -> textColor
            }
        )
    }

    @Composable
    fun cursorColor(isError: Boolean): State<Color> {
        return rememberUpdatedState(if (isError) errorCursorColor else cursorColor)
    }
}

fun Modifier.indicatorLine(
    enabled: Boolean,
    isError: Boolean,
    interactionSource: InteractionSource,
    colors: CustomTextFieldColors,
    focusedIndicatorLineThickness: Dp = TextFieldDefaults.FocusedIndicatorThickness,
    unfocusedIndicatorLineThickness: Dp = TextFieldDefaults.UnfocusedIndicatorThickness
): Modifier = composed(inspectorInfo = debugInspectorInfo {
    name = "indicatorLine"
    properties["enabled"] = enabled
    properties["isError"] = isError
    properties["interactionSource"] = interactionSource
    properties["colors"] = colors
    properties["focusedIndicatorLineThickness"] = focusedIndicatorLineThickness
    properties["unfocusedIndicatorLineThickness"] = unfocusedIndicatorLineThickness
}) {
    val stroke by animateBorderStrokeAsState(
        enabled,
        isError,
        interactionSource,
        colors,
        focusedIndicatorLineThickness,
        unfocusedIndicatorLineThickness
    )
    drawWithContent {
        drawContent()
        val strokeWidthDp = stroke.width
        if (strokeWidthDp == Dp.Hairline) return@drawWithContent
        val strokeWidth = strokeWidthDp.value * density
        val y = size.height - strokeWidth / 2
        drawLine(
            stroke.brush,
            Offset(0f, y),
            Offset(size.width, y),
            strokeWidth
        )
    }
}

@Composable
private fun animateBorderStrokeAsState(
    enabled: Boolean,
    isError: Boolean,
    interactionSource: InteractionSource,
    colors: CustomTextFieldColors,
    focusedBorderThickness: Dp,
    unfocusedBorderThickness: Dp
): State<BorderStroke> {
    val focused by interactionSource.collectIsFocusedAsState()
    val indicatorColor = colors.indicatorColor(enabled, isError, interactionSource)
    val targetThickness = if (focused) focusedBorderThickness else unfocusedBorderThickness
    val animatedThickness = if (enabled) {
        @Suppress("AnimateAsStateLabel")
        animateDpAsState(targetThickness, tween(durationMillis = AnimationDuration))
    } else {
        rememberUpdatedState(unfocusedBorderThickness)
    }
    return rememberUpdatedState(
        BorderStroke(animatedThickness.value, SolidColor(indicatorColor.value))
    )
}

private const val AnimationDuration = 150
