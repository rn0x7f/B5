package com.todasbrillamos.model.data

/**
 * Data class que representa una solicitud de pago.
 *
 * @property amount La cantidad total a pagar en la transacción.
 * @property currency La moneda en la que se realizará el pago (por ejemplo, "USD", "EUR").
 * @property description Una descripción de la transacción o del pago.
 * @property source La fuente o método de pago utilizado (por ejemplo, un token de tarjeta de crédito).
 */
data class PaymentRequest(
    val amount: Int,
    val currency: String,
    val description: String,
    val source: String
)