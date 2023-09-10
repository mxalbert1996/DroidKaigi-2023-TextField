package com.mxalbert.compose.textfield.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

@Composable
fun Demo(
    content: @Composable () -> Unit
) {
    val newDensity = LocalDensity.current.run {
        Density(density * 1.5f, fontScale * (3f / 2f))
    }
    CompositionLocalProvider(LocalDensity provides newDensity) {
        AppTheme {
            Surface(
                color = MaterialTheme.colorScheme.background,
                content = content
            )
        }
    }
}
