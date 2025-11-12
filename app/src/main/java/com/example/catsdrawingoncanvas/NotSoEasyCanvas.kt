package com.example.catsdrawingoncanvas

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

interface NotSoEasyCacheDrawScope: Density {
    val size: Size
    val layoutDirection: LayoutDirection
    fun onDrawBehind(block: DrawScope.() -> Unit): DrawResult
    fun onDrawWithContent(block: ContentDrawScope.() -> Unit): DrawResult

    val contentRect: Rect
    val contentScale: CanvasContentScale
    val contentMatrix: Matrix
    fun DrawScope.withContentTransform(block: DrawScope.() -> Unit)
}

private class NotSoEasyCacheDrawScopeImpl(
    private val controller: NotSoEasyCanvasController,
    private val originScope: CacheDrawScope
): NotSoEasyCacheDrawScope {
    override val size: Size
        get() = originScope.size

    override val layoutDirection: LayoutDirection
        get() = originScope.layoutDirection

    override val density: Float
        get() = originScope.density

    override val fontScale: Float
        get() = originScope.fontScale

    override val contentRect: Rect
        get() = controller.contentRect

    override val contentScale: CanvasContentScale
        get() = controller.contentScale

    override val contentMatrix: Matrix
        get() = controller.contentMatrix

    override fun onDrawBehind(block: DrawScope.() -> Unit): DrawResult {
        return originScope.onDrawBehind(block)
    }

    override fun onDrawWithContent(block: ContentDrawScope.() -> Unit): DrawResult {
        return originScope.onDrawWithContent(block)
    }

    override fun DrawScope.withContentTransform(block: DrawScope.() -> Unit) {
        withTransform(
            transformBlock = {
                transform(contentMatrix)
            },
            drawBlock = {
                block()
            }
        )
    }
}

@Composable
fun NotSoEasyCanvas(
    controller: NotSoEasyCanvasController,
    modifier: Modifier,
    useGraphicLayer: Boolean = false,
    onDraw: NotSoEasyCacheDrawScope.() -> DrawResult
) {
    val graphicLayerModifier = if (useGraphicLayer) {
        Modifier.graphicsLayer {
            controller.setCanvasSize(size)
            translationX = controller.offset.x
            translationY = controller.offset.y
            scaleX = controller.zoom
            scaleY = controller.zoom
            transformOrigin = TransformOrigin(0f, 0f)

        }
    } else {
        Modifier
    }
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { offset ->
                        controller.focusOnViewPoint(offset)
                    }
                )
            }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, _ ->
                    controller.panAndZoom(centroid, pan, zoom)
                }
            }
            .then(graphicLayerModifier)
            .drawWithCache {
                controller.setCanvasSize(size)
                val scope = NotSoEasyCacheDrawScopeImpl(controller, this)
                scope.onDraw()
            }
    )
}
