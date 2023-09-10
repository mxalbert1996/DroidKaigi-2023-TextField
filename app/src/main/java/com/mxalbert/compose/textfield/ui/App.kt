package com.mxalbert.compose.textfield.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.mxalbert.compose.textfield.R
import com.mxalbert.compose.textfield.ui.demo.CustomTextFieldDemo
import com.mxalbert.compose.textfield.ui.demo.NumberWithSeparatorDemo
import com.mxalbert.compose.textfield.ui.demo.PinTextFieldDemo
import com.mxalbert.compose.textfield.ui.demo.TextLengthFilterDemo

@Composable
fun App() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            var currentIndex: Int? by rememberSaveable { mutableStateOf(null) }
            Crossfade(
                targetState = currentIndex,
                label = "ScreenTransition",
                modifier = Modifier.fillMaxSize()
            ) { index ->
                Column {
                    TopAppBar(
                        index = index,
                        onNavigateUpClick = { currentIndex = null },
                        modifier = Modifier.zIndex(1f)
                    )
                    if (index == null) {
                        DemoList(onDemoClick = { currentIndex = it })
                    } else {
                        Demos[index].content()
                    }
                }
            }
            BackHandler(enabled = currentIndex != null) {
                currentIndex = null
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    index: Int?,
    onNavigateUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = index?.let { Demos[it].title }
                    ?: stringResource(R.string.app_name)
            )
        },
        navigationIcon = {
            if (index != null) {
                IconButton(onClick = onNavigateUpClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_up)
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun DemoList(
    onDemoClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(Demos) { i, demo ->
            ListItem(
                headlineContent = { Text(text = demo.title) },
                modifier = Modifier.clickable { onDemoClick(i) }
            )
        }
    }
}

private class DemoItem(
    val title: String,
    val content: @Composable () -> Unit
)

private val Demos = listOf(
    DemoItem("Text Length Filter") { TextLengthFilterDemo() },
    DemoItem("Number With Separator") { NumberWithSeparatorDemo() },
    DemoItem("Custom Text Field") { CustomTextFieldDemo() },
    DemoItem("Pin Text Field") { PinTextFieldDemo() }
)
