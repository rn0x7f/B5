package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.Pager


@Preview
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
// Definir el gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    val uriHandler = LocalUriHandler.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors))
            .padding(28.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start // Alineamos al inicio horizontalmente
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center // Alineamos el contenido de la Row al inicio
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo zazil",
                        modifier = Modifier.size(150.dp) // Ajusta el tamaño de la imagen
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center // Alineamos el contenido de la Row al inicio
                ) {
                    Pager(images = listOf(
                        R.drawable.imgprueba,
                        R.drawable.foto_acerca_de
                    ))
                }
            }
        }
    }
}

