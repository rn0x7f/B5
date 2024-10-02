package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogAPI {
    @GET("/api/catalogs/{catalogo_id}")
    suspend fun getCatalogById(@Path("catalogo_id") catalogo_id: Int): Response<CatalogInfo>
}