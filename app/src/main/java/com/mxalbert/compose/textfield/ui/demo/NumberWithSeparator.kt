package com.mxalbert.compose.textfield.ui.demo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mxalbert.compose.textfield.ui.Demo
import com.mxalbert.compose.textfield.ui.TextFieldTextStyle
import com.mxalbert.compose.textfield.ui.requestFocusOnce

@Preview
@Composable
fun NumberWithSeparatorDemo() {
    Demo {
        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { value ->
                text = if (value.all { it.isDigit() }) {
                    value
                } else {
                    value.filter { it.isDigit() }
                }
            },
            visualTransformation = SeparatorVisualTransformation,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Number
            ),
            textStyle = TextFieldTextStyle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .requestFocusOnce()
        )
    }
}

private val SeparatorVisualTransformation = VisualTransformation { text ->
    if (text.length <= 3) {
        return@VisualTransformation TransformedText(text, OffsetMapping.Identity)
    }

    val transformedLength = text.length + (text.length - 1) / 3
    val firstGroup = (text.length - 1) % 3 + 1
    val transformed = AnnotatedString.Builder(transformedLength).apply {
        var pos = firstGroup
        append(text, 0, pos)
        while (pos < text.length) {
            append(Separator)
            append(text, pos, pos + 3)
            pos += 3
        }
    }.toAnnotatedString()

    TransformedText(
        text = transformed,
        offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int =
                (offset + (offset + 3 - firstGroup) / 3).coerceAtMost(transformed.length)

            override fun transformedToOriginal(offset: Int): Int =
                offset - (offset + 3 - firstGroup) / 4
        }
    )
}

private const val Separator = ','
