package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI
{
    @GET("/api/users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserInfo>

    @PUT("/api/users/{email}")
    suspend fun updateUser(@Path("email") email: String, @Body userInfo: DataChangeRequest): Response<UserInfo>
}

data class DataChangeRequest(
    val correo_electronico: String,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val contrasena: String
)