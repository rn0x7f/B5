package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StripeAPI {
    @POST("/api/stripe/create-payment-intent")
    suspend fun createPaymentIntent(@Body paymentRequest: String): Response<PaymentIntentResponse>
}


data class PaymentIntentResponse(
    val clientSecret: String,
    val status: String
)
