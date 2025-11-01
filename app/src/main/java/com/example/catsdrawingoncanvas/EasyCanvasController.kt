package com.example.catsdrawingoncanvas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Matrix

@Composable
fun rememberEasyCanvasController(
    initialContentRect: Rect,
    initialContentScale: CanvasContentScale
): EasyCanvasController {
    return remember(initialContentRect, initialContentScale) {
        EasyCanvasController(initialContentRect, initialContentScale)
    }
}

class EasyCanvasController(
    initialContentRect: Rect,
    initialContentScale: CanvasContentScale,
) {
    val contentRect: Rect = initialContentRect
    val contentScale: CanvasContentScale = initialContentScale

    fun getContentMatrix(size: Size): Matrix {
        return Matrix().apply {
            translate(
                x = size.center.x,
                y = size.center.y
            )
            val zoom = calculateContentZoom(
                canvasSize = size,
                contentSize = contentRect.size,
                canvasContentScale = contentScale
            )
            scale(
                x = zoom,
                y = zoom
            )
            translate(
                x = -contentRect.center.x,
                y = -contentRect.center.y
            )
        }
    }
}
