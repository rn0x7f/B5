package com.todasbrillamos.model.data

/**
 * Data class que representa la información de un producto.
 *
 * @property nombre El nombre del producto.
 * @property descripcion Una descripción detallada del producto.
 * @property precio El precio del producto.
 * @property categoria La categoría a la que pertenece el producto.
 * @property imagen URL de la imagen del producto.
 * @property id_catalogo El identificador del catálogo al que pertenece el producto.
 * @property id_producto El identificador único del producto.
 */
data class ProductInfo(
    val nombre: String,
    val descripcion: String,
    val precio: Float,
    val categoria: String,
    val imagen: String,
    val id_catalogo: Int,
    val id_producto: Int
)