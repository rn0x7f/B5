package com.todasbrillamos.model.data

data class ProductInfo(
    val catalogId: Int = -1,
    val productId: Int = -1,
    val name: String = "",
    val description: String = "",
    val price: Float = -1f,
    val category: String = "",
    val quantity: Int = 0,
    val image: String = ""
)
