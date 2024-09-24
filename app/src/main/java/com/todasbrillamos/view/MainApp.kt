package com.todasbrillamos.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.todasbrillamos.view.pantallas.AcercaScreen
import com.todasbrillamos.view.pantallas.HomeScreen
import com.todasbrillamos.view.pantallas.SignUpScreen

//contiene todas las pantallas de la app

/**
 * Contiene la pantalla principal de la app.
 * @param modifier modificador para personalizar la apariencia de la pantalla.
 */
@Composable
fun MainApp(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ){
        HomeScreen()

    }

}
