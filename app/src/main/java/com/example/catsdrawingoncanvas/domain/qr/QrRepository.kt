package com.example.catsdrawingoncanvas.domain.qr

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QrRepository @Inject constructor() {
    private val qrMatrix: QrMatrix? = QrMatrix.create(QrMatrix.DEFAULT_MATRIX_SIZE)

    fun getMatrix(): QrMatrix? = qrMatrix

    fun refreshQrMatrix(): QrMatrix? {
        qrMatrix?.generateQrMatrix()
        return getMatrix()
    }
}
