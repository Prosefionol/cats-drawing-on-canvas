package com.example.catsdrawingoncanvas.ui.screens.qr

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catsdrawingoncanvas.domain.CanvasContentScale
import com.example.catsdrawingoncanvas.domain.qr.EasyCanvas
import com.example.catsdrawingoncanvas.domain.qr.QrMatrix
import com.example.catsdrawingoncanvas.domain.qr.rememberEasyCanvasController

@Composable
fun Playground(
    qrMatrix: QrMatrix?,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedGradient by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "GradientAnimation"
    )
    val controller = rememberEasyCanvasController(
        initialContentRect = Rect(0f, 0f, 800f, 800f),
        initialContentScale = CanvasContentScale.CenterInside
    )
    EasyCanvas(
        controller = controller,
        modifier = modifier.sizeIn(250.dp, 250.dp)
    ) {
        val qrMatrixSize = qrMatrix?.getQrMatrixSize() ?: QrMatrix.DEFAULT_MATRIX_SIZE
        val cellSize = contentRect.size / qrMatrixSize.toFloat()
        val transformedContentRect = contentMatrix.map(contentRect)
        onDrawBehind {
            val gradientStart = size.width * animatedGradient
            val gradientEnd = size.width * (animatedGradient + 0.5f)
            val brush = Brush.linearGradient(
                start = Offset(gradientStart, gradientStart),
                end = Offset(gradientEnd, gradientEnd),
                tileMode = TileMode.Mirror,
                colors = listOf(Color.Black, Color.Red),
            )
            withContentTransform {
                qrMatrix?.let {
                    for (i in 0 until qrMatrixSize) {
                        for (j in 0 until qrMatrixSize) {
                            val topLeft = Offset(
                                x = cellSize.width * i,
                                y = cellSize.height * j
                            )
                            if (qrMatrix.getQrMatrixValue(i, j) == 1) {
                                drawRect(
                                    brush = brush,
                                    topLeft = topLeft,
                                    size = cellSize
                                )
                            } else {
                                drawRect(
                                    color = Color.White,
                                    topLeft = topLeft,
                                    size = cellSize
                                )
                            }
                        }
                    }
                }
            }
            drawRect(
                color = Color.Black,
                topLeft = transformedContentRect.topLeft,
                size = transformedContentRect.size,
                style = Stroke(width = 3.dp.toPx())
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaygroundPreview() {
    Playground(
        qrMatrix = QrMatrix.create(QrMatrix.DEFAULT_MATRIX_SIZE)
    )
}
