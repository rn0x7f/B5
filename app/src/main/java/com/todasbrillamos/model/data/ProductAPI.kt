package com.todasbrillamos.model.data

import retrofit2.http.GET

interface ProductAPI {
    @GET("products")
    suspend fun getProducts(): List<ProductInfo>
}