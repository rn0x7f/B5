package com.todasbrillamos.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.todasbrillamos.view.pantallas.AcercaScreen
import com.todasbrillamos.view.pantallas.HomeScreen

/**
 * Actividad principal de la app
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeScreen()
        }
    }
}


