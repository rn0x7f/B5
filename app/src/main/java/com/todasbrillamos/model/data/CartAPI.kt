package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface CartAPI {
    @POST("/api/carts/")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<Cart>

    @DELETE("/api/carts/{email}/{id_producto}")
    suspend fun removeFromCart(@Path("email") email: String, @Path("id_producto") id_producto: Int): Response<Cart>

    @POST("/api/carts/{email}")
    suspend fun deleteCart(@Path("email") email: String): Response<DeleteCartResponse>
}

data class AddToCartRequest(
    val usuario_correo_electronico: String,
    val producto_id: Int,
    val cantidad: Int
)

data class Cart(
    val usuario_correo_electronico: String,
    val producto_id: Int,
    val cantidad: Int
)

data class DeleteCartResponse(
    val msg: String
)