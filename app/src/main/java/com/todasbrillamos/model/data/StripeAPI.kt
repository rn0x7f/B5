package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StripeAPI {
    @FormUrlEncoded
    @POST("create-payment-intent")
    suspend fun createPaymentIntent(
        @Field("amount") amount: Int,
        @Field("currency") currency: String
    ): Response<PaymentIntentResponse>
}

data class PaymentIntentResponse(
    val clientSecret: String
)
