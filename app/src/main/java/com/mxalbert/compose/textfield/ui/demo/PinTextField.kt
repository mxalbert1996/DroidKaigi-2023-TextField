package com.mxalbert.compose.textfield.ui.demo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mxalbert.compose.textfield.ui.Demo
import com.mxalbert.compose.textfield.ui.requestFocusOnce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter

class VerifyPinViewModel {

    var textFieldValue by mutableStateOf(TextFieldValue())
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set

    val snackbarHostState = SnackbarHostState()

    suspend fun run() {
        snapshotFlow { textFieldValue.text }
            .filter { it.length == MaxDigits }
            .collectLatest { digits ->
                validatePin(digits)
            }
    }

    fun onTextFieldValueChange(value: TextFieldValue) {
        val text = value.text
        if (text.length <= MaxDigits && text.all { it.isDigit() }) {
            textFieldValue = value
        }
    }

    private suspend fun validatePin(digits: String): Boolean {
        isLoading = true
        delay(1000L)

        textFieldValue = TextFieldValue()
        isLoading = false
        snackbarHostState.showSnackbar("Invalid!")
        return false
    }
}

@Preview
@Composable
fun PinTextFieldDemo() {
    Demo {
        val viewModel = remember { VerifyPinViewModel() }
        LaunchedEffect(viewModel) {
            viewModel.run()
        }

        Box(contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                PinField(
                    value = viewModel.textFieldValue,
                    onValueChange = viewModel::onTextFieldValueChange,
                    enabled = !viewModel.isLoading,
                    modifier = Modifier.requestFocusOnce()
                )

                val focusManager = LocalFocusManager.current
                Button(onClick = { focusManager.clearFocus() }) {
                    Text(text = "Clear Focus")
                }

                Spacer(modifier = Modifier.weight(1f))

                SnackbarHost(hostState = viewModel.snackbarHostState)
            }

            AnimatedVisibility(
                visible = viewModel.isLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f))
                        .pointerInput(Unit) {}
                        .wrapContentSize()
                )
            }
        }
    }
}

@Composable
private fun PinField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val contentColor = LocalContentColor.current.copy(alpha = if (enabled) 1f else 0.38f)

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.None
        ),
        interactionSource = interactionSource,
        modifier = modifier.border(1.dp, contentColor, RoundedCornerShape(8.dp))
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            PinDigits(value.text, interactionSource.collectIsFocusedAsState().value)
        }
    }
}

@Composable
private fun PinDigits(
    digits: String,
    focused: Boolean,
    modifier: Modifier = Modifier
) {
    val focusedColor = LocalTextSelectionColors.current.backgroundColor
    val contentColor = LocalContentColor.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        repeat(MaxDigits) { i ->
            Text(
                text = digits.getOrNull(i)?.toString() ?: " ",
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.drawWithContent {
                    if (i == digits.length && focused) {
                        drawRect(color = focusedColor)
                    }
                    drawContent()
                    if (i >= digits.length) {
                        drawLine(
                            color = contentColor,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                }
            )
        }
    }
}

private const val MaxDigits = 4
