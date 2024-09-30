package com.todasbrillamos.model

import com.todasbrillamos.model.data.ProductInfo
import com.todasbrillamos.model.data.ProductAPI
import com.todasbrillamos.model.data.UserAPI
import com.todasbrillamos.model.data.UserInfo
import retrofit2.Retrofit

class RemoteConnecter {
    private val retrofitClient by lazy {
        Retrofit.Builder()
            .baseUrl("TODO: URL")
            .build()
    }

    private val ClientService by lazy {
        retrofitClient.create(UserAPI::class.java)
    }

    private val ProductService by lazy {
        retrofitClient.create(ProductAPI::class.java)
    }

    suspend fun getUsers(): List<UserInfo> {
        return ClientService.getUsers()
    }
}