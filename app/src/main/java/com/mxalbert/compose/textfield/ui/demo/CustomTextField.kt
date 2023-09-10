package com.mxalbert.compose.textfield.ui.demo

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxalbert.compose.textfield.ui.CheckBox
import com.mxalbert.compose.textfield.ui.Demo

@Preview
@Composable
fun CustomTextFieldDemo() {
    Demo {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            var enabled by rememberSaveable { mutableStateOf(true) }
            var isError by rememberSaveable { mutableStateOf(false) }

            CheckBox(text = "enabled", checked = enabled, onCheckedChange = { enabled = it })
            CheckBox(text = "isError", checked = isError, onCheckedChange = { isError = it })
            val focusManager = LocalFocusManager.current
            Button(onClick = { focusManager.clearFocus() }) {
                Text(text = "Clear Focus")
            }

            var value by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue())
            }
            CustomTextField(
                value = value,
                onValueChange = { value = it },
                singleLine = true,
                enabled = enabled,
                isError = isError,
                label = { Text(text = "CustomTextField") },
                placeholder = { Text(text = "プレースホルダ") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Proportional,
                        trim = LineHeightStyle.Trim.None
                    ),
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CustomTextFieldColors = CustomTextFieldDefaults.colors()
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse { colors.textColor(enabled, isError).value }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.indicatorLine(enabled, isError, interactionSource, colors),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        decorationBox = { innerTextField ->
            DecorationBox(
                value = value,
                innerTextField = innerTextField,
                visualTransformation = visualTransformation,
                enabled = enabled,
                isError = isError,
                label = label,
                placeholder = placeholder,
                textStyle = textStyle,
                interactionSource = interactionSource,
                colors = colors,
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
    )
}

@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CustomTextFieldColors = CustomTextFieldDefaults.colors()
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse { colors.textColor(enabled, isError).value }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.indicatorLine(enabled, isError, interactionSource, colors),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        decorationBox = { innerTextField ->
            DecorationBox(
                value = value.text,
                innerTextField = innerTextField,
                visualTransformation = visualTransformation,
                enabled = enabled,
                isError = isError,
                label = label,
                placeholder = placeholder,
                textStyle = textStyle,
                interactionSource = interactionSource,
                colors = colors,
                modifier = Modifier.padding(bottom = 3.dp)
            )
        }
    )
}

@Composable
private fun DecorationBox(
    value: String,
    innerTextField: @Composable () -> Unit,
    visualTransformation: VisualTransformation,
    enabled: Boolean,
    isError: Boolean,
    label: @Composable (() -> Unit)?,
    placeholder: @Composable (() -> Unit)?,
    textStyle: TextStyle,
    interactionSource: InteractionSource,
    colors: CustomTextFieldColors,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        if (label != null) {
            WithStyle(
                contentColor = colors.labelColor(enabled, isError, interactionSource).value,
                textStyle = MaterialTheme.typography.bodySmall,
                content = label
            )
        }

        val transformedText = remember(value, visualTransformation) {
            visualTransformation.filter(AnnotatedString(value))
        }.text.text

        Box {
            innerTextField()
            if (placeholder != null && transformedText.isEmpty()) {
                WithStyle(
                    contentColor = colors.placeholderColor(enabled).value,
                    textStyle = textStyle,
                    content = placeholder
                )
            }
        }
    }
}

@Composable
private fun WithStyle(
    contentColor: Color,
    textStyle: TextStyle? = null,
    content: @Composable () -> Unit
) {
    val withColor = @Composable {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            content = content
        )
    }
    if (textStyle != null) ProvideTextStyle(textStyle, withColor) else withColor()
}
