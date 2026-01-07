package com.example.catsdrawingoncanvas.ui.screens.qr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.catsdrawingoncanvas.R
import com.example.catsdrawingoncanvas.domain.qr.QrMatrix
import com.example.catsdrawingoncanvas.ui.components.VSpacer
import com.example.catsdrawingoncanvas.ui.screens.qr.QrViewModel.QrScreenState

@Composable
fun QrScreen() {
    val viewModel: QrViewModel = hiltViewModel()
    val qrScreenState by viewModel.stateFlow.collectAsState()

    QrContent(
        qrScreenState,
        viewModel
    )
}

@Composable
fun QrContent(
    qrScreenState: QrScreenState,
    controller: QrController
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState()
            )
    ) {
        Text(
            text = stringResource(R.string.card_number),
            fontSize = 22.sp
        )
        VSpacer(
            size = 16.dp
        )
        Playground(
            qrMatrix = qrScreenState.qrMatrix,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        VSpacer(
            size = 16.dp
        )
        Text(
            text = stringResource(R.string.qr_timer_text, qrScreenState.qrValidTime),
            fontSize = 18.sp
        )
        VSpacer(
            size = 16.dp
        )
        Button(
            onClick = controller::refreshQr,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(R.string.qr_screen_button)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQrScreen() {
    val previewController = object: QrController {
        override fun refreshQr() = Unit
    }
    val previewState = QrScreenState(
        qrMatrix = QrMatrix.create(QrMatrix.DEFAULT_MATRIX_SIZE),
        qrValidTime = 15
    )

    QrContent(
        previewState,
        previewController
    )
}
