package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todasbrillamos.view.componentes.NavBar

@Preview
@Composable
fun ItemScreen(modifier: Modifier = Modifier) {
// Definir el gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    val uriHandler = LocalUriHandler.current

    Scaffold(
        bottomBar = { NavBar() }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(28.dp)
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors = gradientColors))
            ) {
            }
        }
    }
}