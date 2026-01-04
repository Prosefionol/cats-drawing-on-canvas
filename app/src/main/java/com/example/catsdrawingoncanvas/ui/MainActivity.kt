package com.example.catsdrawingoncanvas.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.catsdrawingoncanvas.ui.navigation.AppNavigationBar
import com.example.catsdrawingoncanvas.ui.navigation.KaleidoscopeGraph
import com.example.catsdrawingoncanvas.ui.navigation.LocalNavController
import com.example.catsdrawingoncanvas.ui.navigation.MainTabs
import com.example.catsdrawingoncanvas.ui.navigation.QrGraph
import com.example.catsdrawingoncanvas.ui.screens.kaleidoscope.AnotherPlayground
import com.example.catsdrawingoncanvas.ui.screens.qr.Playground
import com.example.catsdrawingoncanvas.ui.theme.CatsDrawingOnCanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatsDrawingOnCanvasTheme {
                CatsDrawingOnCanvasApp()
            }
        }
    }
}

@Composable
fun CatsDrawingOnCanvasApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            AppNavigationBar(
                navController = navController,
                tabs = MainTabs
            )
        }
    ) { paddingValues ->
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = QrGraph,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                navigation<QrGraph>(
                    startDestination = QrGraph.QrRoute
                ) {
                    composable<QrGraph.QrRoute> {
                        Playground(
                            qrMatrixSize = 30,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                navigation<KaleidoscopeGraph>(
                    startDestination = KaleidoscopeGraph.KaleidoscopeRoute
                ) {
                    composable<KaleidoscopeGraph.KaleidoscopeRoute> {
                        AnotherPlayground(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
