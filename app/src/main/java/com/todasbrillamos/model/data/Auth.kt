package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

data class SignupRequest(
    val email: String,
    val name: String,
    val lastName: String,
    val phone: String,
    val password: String
)

data class TokenResponse(
    val token: String
)

interface Auth {
    @POST("/api/auth/usuario/signup")
    suspend fun signup(
        @Body request: SignupRequest
    ): Response<String>

    @POST("/api/auth/usuario/signin")
    suspend fun signin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<TokenResponse>

    @POST("/api/auth/usuario/signout")
    suspend fun signout(): Response<String>
}
