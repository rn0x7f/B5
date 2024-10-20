package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interfaz de servicio que define las operaciones relacionadas con el catálogo de productos usando Retrofit.
 */
interface CatalogAPI {
    /**
     * Obtiene la información de un catálogo específico basado en su identificador.
     *
     * @param catalogo_id El identificador único del catálogo que se desea obtener.
     * @return Respuesta que contiene la información del catálogo solicitado.
     */
    @GET("/api/catalogs/{catalogo_id}")
    suspend fun getCatalogById(@Path("catalogo_id") catalogo_id: Int): Response<CatalogInfo>
}