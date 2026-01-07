package com.example.catsdrawingoncanvas.ui.screens.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catsdrawingoncanvas.domain.qr.QrMatrix
import com.example.catsdrawingoncanvas.domain.qr.QrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val qrRepository: QrRepository
): ViewModel(), QrController {
    private val _stateFlow = MutableStateFlow(QrScreenState(qrRepository.getMatrix()))
    val stateFlow: StateFlow<QrScreenState> = _stateFlow

    init {
        startQrTimer()
    }

    private fun startQrTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_stateFlow.value.qrValidTime > 0) {
                    _stateFlow.update {
                        it.copy(
                            qrValidTime = it.qrValidTime - 1
                        )
                    }
                } else {
                    _stateFlow.update {
                        it.copy(
                            qrMatrix = qrRepository.refreshQrMatrix(),
                            qrValidTime = DEFAULT_QR_VALID_TIME
                        )
                    }
                }
            }
        }
    }

    override fun refreshQr() {
        _stateFlow.update {
            it.copy(
                qrMatrix = qrRepository.refreshQrMatrix(),
                qrValidTime = DEFAULT_QR_VALID_TIME
            )
        }
    }

    data class QrScreenState(
        val qrMatrix: QrMatrix?,
        val qrValidTime: Int = DEFAULT_QR_VALID_TIME
    )

    companion object {
        const val DEFAULT_QR_VALID_TIME = 15
    }
}
