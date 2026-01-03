package com.example.catsdrawingoncanvas.domain.qr

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.DrawResult
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.example.catsdrawingoncanvas.domain.CanvasContentScale

interface EasyCacheDrawScope: Density {
    val size: Size
    val layoutDirection: LayoutDirection
    fun onDrawBehind(block: DrawScope.() -> Unit): DrawResult
    fun onDrawWithContent(block: ContentDrawScope.() -> Unit): DrawResult

    val contentRect: Rect
    val contentScale: CanvasContentScale
    val contentMatrix: Matrix
    fun DrawScope.withContentTransform(block: DrawScope.() -> Unit)
}

private class EasyCacheDrawScopeImpl(
    private val controller: EasyCanvasController,
    private val originScope: CacheDrawScope
): EasyCacheDrawScope {
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
        get() = controller.getContentMatrix(size)

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
fun EasyCanvas(
    controller: EasyCanvasController,
    modifier: Modifier,
    onDraw: EasyCacheDrawScope.() -> DrawResult
) {
    Box(
        modifier = modifier
            .drawWithCache {
                val scope = EasyCacheDrawScopeImpl(controller, this)
                scope.onDraw()
            }
    )
}
