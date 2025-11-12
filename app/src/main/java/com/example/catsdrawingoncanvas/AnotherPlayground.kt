package com.example.catsdrawingoncanvas

import android.graphics.RuntimeShader
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun AnotherPlayground(
    modifier: Modifier = Modifier
) {
    val shader = RuntimeShader(SHADER_PROGRAM)
    val brush = ShaderBrush(shader)
    val controller = rememberNotSoEasyCanvasController(
        initialContentRect = Rect(
            center = Offset.Zero,
            radius = 2.2f
        ),
        initialContentScale = CanvasContentScale.CenterInside
    )
    NotSoEasyCanvas(
        controller = controller,
        modifier = modifier
            .sizeIn(200.dp, 200.dp),
        useGraphicLayer = true
    ) {
        onDrawBehind {
            drawRect(
                brush = brush,
                topLeft = contentRect.topLeft,
                size = contentRect.size
            )
        }
    }
}
