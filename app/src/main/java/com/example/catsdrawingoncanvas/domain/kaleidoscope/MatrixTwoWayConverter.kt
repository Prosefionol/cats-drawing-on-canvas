package com.example.catsdrawingoncanvas.domain.kaleidoscope

import androidx.compose.animation.core.AnimationVector3D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.ui.graphics.Matrix

object MatrixTwoWayConverter: TwoWayConverter<Matrix, AnimationVector3D> {
    override val convertFromVector: (AnimationVector3D) -> Matrix = { vector ->
        Matrix().apply {
            values[Matrix.Companion.TranslateX] = vector.v1
            values[Matrix.Companion.TranslateY] = vector.v2
            values[Matrix.Companion.ScaleX] = vector.v3
            values[Matrix.Companion.ScaleY] = vector.v3
        }
    }
    override val convertToVector: (Matrix) -> AnimationVector3D = { matrix ->
        AnimationVector3D(
            v1 = matrix.values[Matrix.Companion.TranslateX],
            v2 = matrix.values[Matrix.Companion.TranslateY],
            v3 = matrix.values[Matrix.Companion.ScaleX]
        )
    }
}