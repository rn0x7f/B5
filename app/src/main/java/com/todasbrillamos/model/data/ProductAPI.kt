package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductAPI {
    @GET("/api/products")
    suspend fun getProducts(@Query("skip") skip: Int,
                            @Query("limit") limit: Int): Response<List<ProductInfo>>

    @GET("/api/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<ProductInfo>

    @PUT("/api/products/{id}")
    suspend fun updateProductById(@Path("id") id: Int, @Body product: ProductInfo): Response<ProductInfo>

}