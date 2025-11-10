package com.example.catsdrawingoncanvas

import androidx.compose.animation.core.AnimationVector3D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.ui.graphics.Matrix

object MatrixTwoWayConverter: TwoWayConverter<Matrix, AnimationVector3D> {
    override val convertFromVector: (AnimationVector3D) -> Matrix = { vector ->
        Matrix().apply {
            values[Matrix.TranslateX] = vector.v1
            values[Matrix.TranslateY] = vector.v2
            values[Matrix.ScaleX] = vector.v3
            values[Matrix.ScaleY] = vector.v3
        }
    }
    override val convertToVector: (Matrix) -> AnimationVector3D = { matrix ->
        AnimationVector3D(
            v1 = matrix.values[Matrix.TranslateX],
            v2 = matrix.values[Matrix.TranslateY],
            v3 = matrix.values[Matrix.ScaleX]
        )
    }
}
