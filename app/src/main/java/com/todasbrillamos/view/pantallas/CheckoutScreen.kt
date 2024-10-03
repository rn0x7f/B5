package com.todasbrillamos.view.pantallas

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.todasbrillamos.viewmodel.MainVM
import kotlinx.coroutines.launch
@Composable
fun CheckoutScreen(
    navController: NavHostController,
    mainVM: MainVM, // ViewModel pasado como parámetro
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current as ComponentActivity
    var clientSecret by remember { mutableStateOf<String?>(null) }
    var paymentResult by remember { mutableStateOf<PaymentSheetResult?>(null) }

    // Inicializamos PaymentSheet
    val paymentSheet = remember {
        PaymentSheet(context) { paymentSheetResult ->
            paymentResult = paymentSheetResult
        }
    }

    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    // Llamada para obtener el clientSecret (simulación)
    LaunchedEffect(Unit) {
        // Aquí llamarías a tu backend usando la función en el ViewModel.
        // Suponiendo que tienes una función como mainVM.getProducts() o mainVM.createPaymentIntent()
        // Para obtener el clientSecret, podrías tener algo como:
        val secret = "tu_client_secret_simulado" // Simulación
        clientSecret = secret
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Total a pagar: $50.00")

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (clientSecret != null) {
                val configuration = PaymentSheet.Configuration(
                    merchantDisplayName = "Tu Tienda"
                )

                paymentSheet.presentWithPaymentIntent(
                    clientSecret!!,
                    configuration
                )
            }
        }) {
            Text(text = "Pagar con Stripe")
        }

        // Mostrar el resultado del pago
        paymentResult?.let { result ->
            when (result) {
                is PaymentSheetResult.Completed -> {
                    Text(text = "Pago completado con éxito", color = Color.Green)
                }
                is PaymentSheetResult.Canceled -> {
                    Text(text = "Pago cancelado", color = Color.Red)
                }
                is PaymentSheetResult.Failed -> {
                    Text(text = "Error en el pago: ${result.error?.message}", color = Color.Red)
                }
            }
        }
    }
}
