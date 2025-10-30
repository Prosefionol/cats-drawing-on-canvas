package com.example.catsdrawingoncanvas

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun Playground(
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
    Box(
        modifier = modifier
            .sizeIn(300.dp, 300.dp)
            .drawBehind {
                val cellSize = size / 8f
                val gradientStart = size.width * animatedGradient
                val gradientEnd = size.width * (animatedGradient + 0.5f)
                val brush = Brush.linearGradient(
                    start = Offset(gradientStart, gradientStart),
                    end = Offset(gradientEnd, gradientEnd),
                    tileMode = TileMode.Mirror,
                    colors = listOf(Color.White, Color.Blue, Color.Red),

                    )
                for (i in 0..7) {
                    for (j in 0..7) {
                        val topLeft = Offset(x = cellSize.width * i, y = cellSize.height * j)
                        if ((i + j) % 2 == 0) {
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
    )
}
