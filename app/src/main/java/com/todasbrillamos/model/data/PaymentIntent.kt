package com.todasbrillamos.model.data

data class PaymentRequest(
    val amount: Int,
    val currency: String,
    val description: String,
    val source: String
)