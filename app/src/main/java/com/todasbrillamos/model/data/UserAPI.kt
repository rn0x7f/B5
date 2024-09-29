package com.todasbrillamos.model.data

import retrofit2.http.GET

interface UserAPI {
    @GET("users")
    suspend fun getUsers(): List<UserInfo>
}