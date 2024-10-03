package com.todasbrillamos.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.stripe.android.PaymentConfiguration
import com.todasbrillamos.viewmodel.MainVM

/**
 * Actividad principal de la app
 */
class MainActivity : ComponentActivity() {
    private val mainVM: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración inicial de Stripe
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51N6zVhCiCaF6G8C7EgvjiktgMxNTIwaRo9dJD7WAZ062PiH6sxk3lywUZyNU9pQW2ef5wbzCok3QxUalrTHAFG8o00FmdquosV"
        )

        enableEdgeToEdge()

        setContent {
            MainApp(mainVM)
        }
    }
}



