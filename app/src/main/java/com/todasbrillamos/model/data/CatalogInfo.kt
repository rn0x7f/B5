package com.todasbrillamos.model.data

data class CatalogInfo(
    val nombre: String,
    val descripcion: String,
    val id_catalogo: Int,
    val productos: List<ProductInfo>
)
