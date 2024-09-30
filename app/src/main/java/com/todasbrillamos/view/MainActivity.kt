package com.todasbrillamos.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.todasbrillamos.viewmodel.MainVM

/**
 * Actividad principal de la app
 */
class MainActivity : ComponentActivity() {
    private val mainVM : MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp(mainVM)
        }
    }
}


