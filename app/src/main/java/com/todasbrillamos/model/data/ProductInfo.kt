package com.todasbrillamos.model.data

data class ProductInfo(
    val nombre: String,
    val descripcion: String,
    val precio: Float,
    val categoria: String,
    val imagen: String,
    val id_catalogo: Int,
    val id_producto: Int
)
