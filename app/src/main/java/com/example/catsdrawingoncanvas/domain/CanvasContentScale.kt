package com.example.catsdrawingoncanvas.domain

import androidx.compose.ui.geometry.Size
import kotlin.math.max
import kotlin.math.min


enum class CanvasContentScale {
    CenterInside, CenterCrop
}

fun calculateContentZoom(
    canvasSize: Size,
    contentSize: Size,
    canvasContentScale: CanvasContentScale
): Float {
    val horizontalZoom = canvasSize.width / contentSize.width
    val verticalZoom = canvasSize.height / contentSize.height
    return when(canvasContentScale) {
        CanvasContentScale.CenterCrop -> max(horizontalZoom, verticalZoom)
        CanvasContentScale.CenterInside -> min(horizontalZoom, verticalZoom)
    }
}
