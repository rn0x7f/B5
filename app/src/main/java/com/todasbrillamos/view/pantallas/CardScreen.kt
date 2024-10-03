import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

import com.stripe.android.Stripe
import com.stripe.android.model.PaymentMethodCreateParams


@Composable
fun CardScreen(stripe: Stripe) {
    // States for card inputs
    val cardNumber = remember { mutableStateOf("") }
    val expMonth = remember { mutableStateOf("") }
    val expYear = remember { mutableStateOf("") }
    val cvc = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }

    var paymentStatus by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = cardNumber.value,
            onValueChange = { cardNumber.value = it },
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = expMonth.value,
            onValueChange = { expMonth.value = it },
            label = { Text("Expiration Month") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = expYear.value,
            onValueChange = { expYear.value = it },
            label = { Text("Expiration Year") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = cvc.value,
            onValueChange = { cvc.value = it },
            label = { Text("CVC") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = postalCode.value,
            onValueChange = { postalCode.value = it },
            label = { Text("Postal Code") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            scope.launch {
                // Attempt to create the PaymentMethod when the button is clicked
                val cardParams = PaymentMethodCreateParams.Card.Builder()
                    .setNumber(cardNumber.value)
                    .setExpiryMonth(expMonth.value.toIntOrNull() ?: 0)
                    .setExpiryYear(expYear.value.toIntOrNull() ?: 0)
                    .setCvc(cvc.value)
                    .build()

                // Create PaymentMethod with Stripe, and handle the result
                createPaymentMethod(stripe, cardParams) { paymentMethodId ->
                    paymentStatus = if (paymentMethodId != null) {
                        "Payment Method Created: $paymentMethodId"
                    } else {
                        "Failed to create Payment Method"
                    }
                }
            }
        }) {
            Text("Pay Now")
        }

        Text(text = paymentStatus, modifier = Modifier.padding(top = 16.dp))
    }
}

// Function to create Payment Method using Stripe
suspend fun createPaymentMethod(
    stripe: Stripe,
    cardParams: PaymentMethodCreateParams.Card,
    onPaymentMethodCreated: (String?) -> Unit
) {
    // Create the PaymentMethodCreateParams with card details and billing information
    val paymentMethodParams = PaymentMethodCreateParams.create(
        cardParams,
    )

    try {
        // Create the PaymentMethod synchronously using Stripe's SDK
        val paymentMethod = stripe.createPaymentMethodSynchronous(paymentMethodParams)
        onPaymentMethodCreated(paymentMethod?.id)  // Return the Payment Method ID
    } catch (e: Exception) {
        onPaymentMethodCreated(null)  // Handle failure
    }
}