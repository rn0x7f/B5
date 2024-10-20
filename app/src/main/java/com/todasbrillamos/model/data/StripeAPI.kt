package com.todasbrillamos.model.data

import com.stripe.android.model.PaymentIntent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz de servicio que define las operaciones relacionadas con pagos a través de Stripe usando Retrofit.
 */
interface StripeAPI {
    /**
     * Crea un Payment Intent utilizando la API de Stripe.
     *
     * @param paymentRequest Objeto que contiene los detalles del pago a procesar.
     * @return Respuesta que contiene el client secret y el estado del Payment Intent creado.
     */
    @POST("/api/stripe/create-payment-intent")
    suspend fun createPaymentIntent(@Body paymentRequest: PaymentRequest): Response<PaymentIntentResponse>
}

/**
 * Data class que representa la respuesta de la creación de un Payment Intent en Stripe.
 *
 * @property clientSecret El secret que se utiliza para confirmar el Payment Intent en el cliente.
 * @property status El estado del Payment Intent (por ejemplo, "succeeded", "requires_action").
 */
data class PaymentIntentResponse(
    val clientSecret: String,
    val status: String
)