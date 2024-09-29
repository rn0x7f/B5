package com.todasbrillamos.model

import com.todasbrillamos.model.data.ProductInfo
import com.todasbrillamos.model.data.ProductAPI
import retrofit2.Retrofit

class RemoteConnecter {
    private val retrofitClient by lazy {
        Retrofit.Builder()
            .baseUrl("TODO: URL")
            .build()
    }

    private val ClientService by lazy {
        retrofitClient.create(ProductAPI::class.java)
    }

    suspend fun getProducts(): List<ProductInfo> {
        return ClientService.getProducts()
    }
}