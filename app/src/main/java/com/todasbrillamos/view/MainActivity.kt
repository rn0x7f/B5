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
 *
 * Esta actividad se encarga de inicializar la configuración de Stripe y de
 * gestionar la navegación entre pantallas utilizando Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    private val mainVM: MainVM by viewModels() // ViewModel para gestionar la lógica de negocio
    private lateinit var stripe: Stripe // Instancia de Stripe para manejar pagos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa Stripe con tu clave pública
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51N6zVhCiCaF6G8C7EgvjiktgMxNTIwaRo9dJD7WAZ062PiH6sxk3lywUZyNU9pQW2ef5wbzCok3QxUalrTHAFG8o00FmdquosV"
        )

        // Crea una instancia de Stripe
        stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)

        // Habilita el diseño de borde a borde (Edge-to-Edge)
        enableEdgeToEdge()

        // Usa setContent para mostrar el contenido de la aplicación
        setContent {
            // Inicializa el navController para la navegación
            val navController = rememberNavController()

            // Pasamos el navController a la función MainApp que gestiona la navegación
            MainApp(mainVM = mainVM, stripe)
        }
    }
}