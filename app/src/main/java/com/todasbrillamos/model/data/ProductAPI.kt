package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz de servicio que define las operaciones relacionadas con productos usando Retrofit.
 */
interface ProductAPI {
    /**
     * Obtiene una lista de productos con paginación.
     *
     * @param skip La cantidad de productos a omitir (para implementar paginación).
     * @param limit El número máximo de productos a devolver en la respuesta.
     * @return Respuesta que contiene una lista de productos.
     */
    @GET("/api/products")
    suspend fun getProducts(@Query("skip") skip: Int,
                            @Query("limit") limit: Int): Response<List<ProductInfo>>

    /**
     * Obtiene la información de un producto específico basado en su identificador.
     *
     * @param id El identificador único del producto que se desea obtener.
     * @return Respuesta que contiene la información del producto solicitado.
     */
    @GET("/api/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductInfo>
}