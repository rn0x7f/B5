package com.todasbrillamos.view

import CardScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.stripe.android.PaymentConfiguration
import com.stripe.android.Stripe
import com.todasbrillamos.viewmodel.MainVM

/**
 * Actividad principal de la app
 */
class MainActivity : ComponentActivity() {
    private val mainVM: MainVM by viewModels()
    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Stripe with your publishable key
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51N6zVhCiCaF6G8C7EgvjiktgMxNTIwaRo9dJD7WAZ062PiH6sxk3lywUZyNU9pQW2ef5wbzCok3QxUalrTHAFG8o00FmdquosV"
        )

        // Create an instance of Stripe
        stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)

        enableEdgeToEdge()

        // Use setContent to display the HomeScreen composable
        setContent {
            // Inicializamos el navController para la navegación
            val navController = rememberNavController()

            // Pasamos el navController a la HomeScreen
            MainApp(mainVM = mainVM, stripe)
        }
    }
}
