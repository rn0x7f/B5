package com.todasbrillamos.model.data

/**
 * Data class que representa un item en el carrito de compras
 *
 * @property product La información del producto que está en el carrito
 * @property quantity La cantidad del producto en el carrito
 */
data class CartItem(
    val product: ProductInfo,
    var quantity: Int
)
