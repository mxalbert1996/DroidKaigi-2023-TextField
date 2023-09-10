package com.mxalbert.compose.textfield.ui.demo

import android.icu.text.BreakIterator
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mxalbert.compose.textfield.ui.Demo
import com.mxalbert.compose.textfield.ui.TextFieldTextStyle
import com.mxalbert.compose.textfield.ui.requestFocusOnce

@Preview
@Composable
fun TextLengthFilterDemo() {
    Demo {
        var value by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }

        val badFilter: (TextFieldValue) -> Unit = {
            if (it.text.length <= 5) {
                value = it
            }
        }

        val naiveFilter: (TextFieldValue) -> Unit = {
            value = when {
                it.text.length <= MaxLength -> it
                value.text.length == MaxLength -> value
                else -> it.copy(
                    annotatedString = it.annotatedString.subSequence(0, MaxLength)
                )
            }
        }

        val betterFilter: (TextFieldValue) -> Unit = {
            value = when {
                it.text.length <= MaxLength -> it
                value.text.length == MaxLength -> value
                else -> {
                    // Same as Android's InputFilter.LengthFilter
                    val text = it.annotatedString
                    val end = if (text[MaxLength - 1].isHighSurrogate()) {
                        MaxLength - 1
                    } else {
                        MaxLength
                    }
                    it.copy(annotatedString = text.subSequence(0, end))
                }
            }
        }

        val bestFilter: (TextFieldValue) -> Unit = {
            value = when {
                it.text.length <= MaxLength -> it
                value.text.length == MaxLength -> value
                else -> {
                    val breakIterator = BreakIterator.getCharacterInstance()
                    breakIterator.setText(it.text)
                    var end = 0
                    while (true) {
                        val newEnd = breakIterator.next()
                        if (newEnd == BreakIterator.DONE || newEnd > MaxLength) {
                            break
                        }
                        end = newEnd
                    }
                    it.copy(annotatedString = it.annotatedString.subSequence(0, end))
                }
            }
        }

        TextField(
            value = value,
            onValueChange = bestFilter,
            textStyle = TextFieldTextStyle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .requestFocusOnce()
        )
    }
}

private const val MaxLength = 5
