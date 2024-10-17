import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.google.gson.Gson

import com.stripe.android.Stripe
import com.stripe.android.model.PaymentMethodCreateParams
import com.todasbrillamos.model.RemoteConnecter
import com.todasbrillamos.model.data.PaymentRequest
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.viewmodel.MainVM

val remoteConnecter = RemoteConnecter()

// Function to create Payment Method using Stripe
suspend fun createPaymentMethod(
    stripe: Stripe,
    cardParams: PaymentMethodCreateParams.Card,
    onPaymentMethodCreated: (String?) -> Unit
) {
    // Create the PaymentMethodCreateParams with card details and billing information
    val paymentMethodParams = PaymentMethodCreateParams.create(cardParams)

    try {
        // Create the PaymentMethod synchronously using Stripe's SDK
        val paymentMethod = stripe.createPaymentMethodSynchronous(paymentMethodParams)
        onPaymentMethodCreated(paymentMethod?.id)  // Return the Payment Method ID
    } catch (e: Exception) {
        onPaymentMethodCreated(null)  // Handle failure
    }
}

/****************/
// Function to create the PaymentMethod
suspend fun createPaymentMethod(
    cardNumber: String,
    expMonth: String,
    expYear: String,
    cvc: String,
    stripe: Stripe
): String? {
    val cardParams = PaymentMethodCreateParams.Card.Builder()
        .setNumber(cardNumber)
        .setExpiryMonth(expMonth.toIntOrNull() ?: 0)
        .setExpiryYear(expYear.toIntOrNull() ?: 0)
        .setCvc(cvc)
        .build()

    // Create PaymentMethod synchronously
    return createPaymentMethodSuspend(stripe, cardParams)
}

// Function to create the PaymentIntent
suspend fun createPaymentIntent(
    paymentMethodId: String,
    total: Float,
    description: String
): String? {
    // Create a PaymentRequest with the necessary details
    val paymentRequest = PaymentRequest(
        amount = total.toInt(),  // Amount in cents (1000.00 MXN)
        currency = "mxn",
        description = description,
        source = paymentMethodId  // Pass the payment method ID
    )

    val gson = Gson()
    val paymentRequestJSON = gson.toJson(paymentRequest)
    println(paymentRequestJSON)

    // Make the API call to the FastAPI backend asynchronously
    return remoteConnecter.createPaymentIntent(paymentRequest)
}

/**************/

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CardScreen(
    stripe: Stripe,
    navController: NavController,
    mainVM: MainVM,
    total: Float,
    description: String
) {
    // States for card inputs
    val cardNumber = remember { mutableStateOf("") }
    val expMonth = remember { mutableStateOf("") }
    val expYear = remember { mutableStateOf("") }
    val cvc = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }

    var paymentStatus by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val gradientColors = listOf(
        Color(0xFFffe5b4),
        Color(0xFFffbba8)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors))
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        TextoResaltado(value = "Total a pagar: $"+ String.format("%.2f", total))
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = cardNumber.value,
            onValueChange = { cardNumber.value = it },
            label = { Text("Número de Tarjeta") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            TextField(
                value = expMonth.value,
                onValueChange = { expMonth.value = it },
                label = { Text("Mes de Expiración") },
                modifier = Modifier.weight(1f).padding(6.dp)
            )

            TextField(
                value = expYear.value,
                onValueChange = { expYear.value = it },
                label = { Text("Año de Expiración") },
                modifier = Modifier.weight(1f).padding(6.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = cvc.value,
            onValueChange = { cvc.value = it },
            label = { Text("CVC") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = postalCode.value,
            onValueChange = { postalCode.value = it },
            label = { Text("Código Postal") },
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            scope.launch {
                // Step 1: Create the PaymentMethod
                val paymentMethodId = createPaymentMethod(
                    cardNumber.value,
                    expMonth.value,
                    expYear.value,
                    cvc.value,
                    stripe
                )

                // Step 2: If PaymentMethod creation succeeded, create the PaymentIntent
                if (paymentMethodId != null) {
                    val desc = description + address.value
                    val clientSecret =
                        createPaymentIntent(paymentMethodId, total * 100f, desc)
                    println("Client Secret: $clientSecret")

                    // Update the payment status
                    paymentStatus = if (clientSecret != null) {
                        // Show success message
                        "¡Pago exitoso!"
                    } else {
                        // Handle failure
                        "Error al crear un Payment Intent con ID: $paymentMethodId"
                    }
                } else {
                    paymentStatus =
                        "Error al crear el método de pago, por favor intente de nuevo más tarde"
                }

                showDialog = true
            }
        }) {
            Text("Pagar")
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = { Text("Estado del Pago") },
                text = { Text(paymentStatus) },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        if (paymentStatus == "¡Pago exitoso!") {
                            // Empty the cart and navigate back to home screen
                            scope.launch {
                                mainVM.addCartToHistory()
                                mainVM.emptyCart()
                                navController.popBackStack()  // Navigate back to the home screen
                            }
                        }
                    }) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

// Refactor the createPaymentMethod function to a suspendable one
suspend fun createPaymentMethodSuspend(
    stripe: Stripe,
    cardParams: PaymentMethodCreateParams.Card
): String? {
    return try {
        val paymentMethodParams = PaymentMethodCreateParams.create(cardParams)
        // Stripe SDK: create payment method synchronously
        val paymentMethod = stripe.createPaymentMethodSynchronous(paymentMethodParams)
        paymentMethod?.id
    } catch (e: Exception) {
        null
    }
}
