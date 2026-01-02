package com.example.catsdrawingoncanvas

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.collections.immutable.persistentListOf

data class AppTab(
    @DrawableRes val icon: Int,
    @StringRes val labelRes: Int,
    val graph: Any
)

val MainTabs = persistentListOf(
    AppTab(
        icon = R.drawable.qr_code,
        labelRes = R.string.qr_screen,
        graph = QrGraph
    ),
    AppTab(
        icon = R.drawable.kaleidoscope,
        labelRes = R.string.kaleidoscope_screen,
        graph = KaleidoscopeGraph
    )
)
