package com.mxalbert.compose.textfield.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequesterModifierNode
import androidx.compose.ui.focus.requestFocus
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import kotlinx.coroutines.launch

fun Modifier.requestFocusOnce(): Modifier =
    this then RequestFocusOnceElement

private data object RequestFocusOnceElement : ModifierNodeElement<RequestFocusOnceNode>() {
    override fun create(): RequestFocusOnceNode = RequestFocusOnceNode()
    override fun update(node: RequestFocusOnceNode) = Unit
    override fun InspectorInfo.inspectableProperties() {
        this.name = "requestFocusOnce"
    }
}

private class RequestFocusOnceNode : FocusRequesterModifierNode, Modifier.Node() {
    override fun onAttach() {
        coroutineScope.launch {
            requestFocus()
        }
    }
}
