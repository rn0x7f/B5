package com.todasbrillamos.model.data

/**
 * Data class que representa la información de un catálogo.
 *
 * @property nombre El nombre del catálogo.
 * @property descripcion Una descripción del catálogo.
 * @property id_catalogo El identificador único del catálogo.
 * @property productos La lista de productos que pertenecen al catálogo.
 */
data class CatalogInfo(
    val nombre: String,
    val descripcion: String,
    val id_catalogo: Int,
    val productos: List<ProductInfo>
)