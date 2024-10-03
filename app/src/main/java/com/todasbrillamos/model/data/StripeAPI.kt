package com.todasbrillamos.model.data

import com.stripe.android.model.PaymentIntent
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface StripeAPI {
    @POST("/api/stripe/create-payment-intent")
    suspend fun createPaymentIntent(@Body paymentRequest: PaymentRequest): Response<PaymentIntentResponse>
}


data class PaymentIntentResponse(
    val clientSecret: String,
    val status: String
)
