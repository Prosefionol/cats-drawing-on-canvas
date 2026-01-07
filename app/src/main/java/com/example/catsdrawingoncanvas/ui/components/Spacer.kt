package com.example.catsdrawingoncanvas.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VSpacer(size: Dp) {
    Spacer(
        modifier = Modifier
            .size(
                width = 0.dp,
                height = size
            )
    )
}
