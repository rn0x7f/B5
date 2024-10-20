package com.todasbrillamos.model.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * interfaz de servicio que define las operaciones relacionadas con el carrito de compras y la base de datos
 */
interface CartAPI {
    /**
     * Agrega un productos al carrito de compras
     *
     * @param addToCartRequest Objeto que contiene la información necesaria para agregar un producto al carrito
     * @return Respuesta que contiene los detalles del carrito
     */
    @POST("/api/carts/")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<Cart>

    /**
     * Elimina un producto del carrito de compras.
     *
     * @param email Correo electrónico del usuario propietario del carrito.
     * @param id_producto Identificador del producto que se desea eliminar del carrito.
     * @return Respuesta que contiene los detalles del carrito actualizado tras la eliminación del producto.
     */
    @DELETE("/api/carts/{email}/{id_producto}")
    suspend fun removeFromCart(@Path("email") email: String, @Path("id_producto") id_producto: Int): Response<Cart>

    /**
     * Elimina todos los productos del carrito de compras del usuario.
     *
     * @param email Correo electrónico del usuario cuyo carrito se desea eliminar.
     * @return Respuesta que incluye un mensaje indicando el éxito o fallo de la operación.
     */
    @POST("/api/carts/{email}")
    suspend fun deleteCart(@Path("email") email: String): Response<DeleteCartResponse>
}

/**
 * Data class que representa la solicitud para agregar un producto al carrito de compras.
 *
 * @property usuario_correo_electronico El correo electrónico del usuario propietario del carrito.
 * @property producto_id El identificador del producto a agregar.
 * @property cantidad La cantidad del producto a agregar.
 */
data class AddToCartRequest(
    val usuario_correo_electronico: String,
    val producto_id: Int,
    val cantidad: Int
)

/**
 * Data class que representa los detalles del carrito de compras.
 *
 * @property usuario_correo_electronico El correo electrónico del usuario propietario del carrito.
 * @property producto_id El identificador del producto en el carrito.
 * @property cantidad La cantidad del producto en el carrito.
 */
data class Cart(
    val usuario_correo_electronico: String,
    val producto_id: Int,
    val cantidad: Int
)

/**
 * Data class que representa la respuesta de la eliminación de un carrito de compras completo.
 *
 * @property msg Mensaje de confirmación de la operación de eliminación del carrito.
 */
data class DeleteCartResponse(
    val msg: String
)