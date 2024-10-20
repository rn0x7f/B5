package com.todasbrillamos.model

import android.util.Log
import com.todasbrillamos.model.data.AddToCartRequest
import com.todasbrillamos.model.data.Auth
import com.todasbrillamos.model.data.CartAPI
import com.todasbrillamos.model.data.CartItem
import com.todasbrillamos.model.data.CatalogAPI
import com.todasbrillamos.model.data.CatalogInfo
import com.todasbrillamos.model.data.DataChangeRequest
import com.todasbrillamos.model.data.PaymentRequest
import com.todasbrillamos.model.data.ProductAPI
import com.todasbrillamos.model.data.ProductInfo
import com.todasbrillamos.model.data.SignInRequest
import com.todasbrillamos.model.data.SignupRequest
import com.todasbrillamos.model.data.StripeAPI
import com.todasbrillamos.model.data.UserAPI
import com.todasbrillamos.model.data.UserInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Clase que maneja la conexión remota con la API del servidor.
 * Proporciona métodos para interactuar con diferentes servicios como usuarios, productos, carritos, y pagos.
 */
class RemoteConnecter {

    // Almacena el correo electrónico del usuario actual.
    var userEmail: String = ""

    // Cliente de Retrofit para la conexión a la API.
    private val retrofitClient by lazy {
        Retrofit.Builder()
            .baseUrl("http://98.82.104.24:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Creación de instancias de API para diferentes servicios.
    private val retrofitUsers by lazy {
        retrofitClient.create(UserAPI::class.java)
    }

    private val retrofitProducts by lazy {
        retrofitClient.create(ProductAPI::class.java)
    }

    private val retrofitCatalogs by lazy {
        retrofitClient.create(CatalogAPI::class.java)
    }

    private val retrofitAuth by lazy {
        retrofitClient.create(Auth::class.java)
    }

    private val retrofitStripe by lazy {
        retrofitClient.create(StripeAPI::class.java)
    }

    private val retrofitCart by lazy {
        retrofitClient.create(CartAPI::class.java)
    }

    /**
     * Obtiene la información de un usuario basado en su correo electrónico.
     *
     * @param email El correo electrónico del usuario.
     * @return La información del usuario o null si no se encuentra.
     */
    suspend fun getUserByEmail(email: String): UserInfo? {
        val response = retrofitUsers.getUserByEmail(email)
        return if (response.isSuccessful) {
            response.body()
        } else {
            when (response.code()) {
                404 -> UserInfo("", "", "", "") // Usuario no encontrado
                500 -> {
                    Log.e("RemoteConnecter", "Internal server error")
                    null
                }
                else -> {
                    Log.e("RemoteConnecter", "Error: ${response.code()}")
                    null
                }
            }
        }
    }

    /**
     * Actualiza la información del usuario.
     *
     * @param dataChangeRequest Objeto que contiene la nueva información del usuario.
     * @return Respuesta que contiene la información actualizada del usuario.
     */
    suspend fun updateUserInfo(dataChangeRequest: DataChangeRequest): Response<UserInfo> {
        return retrofitUsers.updateUser(dataChangeRequest.correo_electronico, dataChangeRequest)
    }

    /**
     * Obtiene una lista de productos con paginación.
     *
     * @return Una lista de productos o null si ocurre un error.
     */
    suspend fun getProducts(): List<ProductInfo>? {
        val response = retrofitProducts.getProducts(skip = 0, limit = 20)
        return if (response.isSuccessful) {
            response.body()
        } else {
            when (response.code()) {
                500 -> {
                    Log.e("RemoteConnecter", "Internal server error")
                    null
                }
                else -> {
                    Log.e("RemoteConnecter", "Error: ${response.code()}")
                    null
                }
            }
        }
    }

    /**
     * Obtiene la información de un producto específico basado en su identificador.
     *
     * @param id El identificador único del producto.
     * @return La información del producto o null si no se encuentra.
     */
    suspend fun getProductById(id: Int): ProductInfo? {
        val response = retrofitProducts.getProductById(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            when (response.code()) {
                404 -> {
                    Log.e("RemoteConnecter", "Product not found")
                    null
                }
                else -> {
                    Log.e("RemoteConnecter", "Error: ${response.code()}")
                    null
                }
            }
        }
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param email El correo electrónico del nuevo usuario.
     * @param name El nombre del nuevo usuario.
     * @param lastName El apellido del nuevo usuario.
     * @param phone El número de teléfono del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @return Mensaje de éxito o null si ocurre un error.
     */
    suspend fun signupUser(
        email: String,
        name: String,
        lastName: String,
        phone: String,
        password: String
    ): String? {
        val requestBody = SignupRequest(email, name, lastName, phone, password)

        val response = retrofitAuth.signup(requestBody)

        return if (response.isSuccessful) {
            "User created successfully"
        } else {
            when (response.code()) {
                400 -> {
                    Log.e("RemoteConnecter", "Bad request")
                    "Este usuario ya existe"
                }
                422 -> {
                    Log.e("RemoteConnecter", "Unprocessable Entity")
                    null
                }
                else -> {
                    Log.e("RemoteConnecter", "Error: ${response.code()}")
                    null
                }
            }
        }
    }

    /**
     * Inicia sesión de un usuario existente.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return El token de acceso o null si ocurre un error.
     */
    suspend fun signinUser(email: String, password: String): String? {
        val requestBody = SignInRequest(email, password)
        println("Connecter: $requestBody")
        val response = retrofitAuth.signin(requestBody)
        return if (response.isSuccessful) {
            response.body()?.token
        } else {
            Log.e("RemoteConnecter", "Error: ${response.code()}")
            null
        }
    }

    /**
     * Crea un Payment Intent utilizando Stripe.
     *
     * @param paymentRequest Objeto que contiene la información del pago.
     * @return El clientSecret para el Payment Intent o null si ocurre un error.
     */
    suspend fun createPaymentIntent(paymentRequest: PaymentRequest): String? {
        return try {
            println("Connecter: $paymentRequest")
            val response = retrofitStripe.createPaymentIntent(paymentRequest)
            if (response.isSuccessful) {
                response.body()?.clientSecret  // Retorna el clientSecret para el PaymentIntent
            } else {
                Log.e("RemoteConnecter", "Failed to create Payment Intent: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RemoteConnecter", "Error: ${e.message}")
            null
        }
    }

    /**
     * Obtiene el correo electrónico del usuario actual.
     *
     * @return El correo electrónico del usuario.
     */
    fun getEmail(): String {
        return userEmail
    }

    /**
     * Establece el correo electrónico del usuario actual.
     *
     * @param email El correo electrónico del usuario.
     */
    fun setEmail(email: String) {
        userEmail = email
    }

    /**
     * Agrega un producto al carrito de compras.
     *
     * @param cartItem El ítem del carrito a agregar.
     * @param quantity La cantidad del producto a agregar.
     * @return True si se agrega exitosamente, de lo contrario false.
     */
    suspend fun addToCart(cartItem: CartItem, quantity: Int = 1): Boolean {
        val newAddToCartRequest =
            AddToCartRequest(getEmail(), cartItem.product.id_producto, quantity)
        val response = retrofitCart.addToCart(newAddToCartRequest)
        println("Email: ${getEmail()},ID: ${cartItem.product.id_producto}, Cantidad: $quantity")
        return if (response.isSuccessful) {
            true
        } else {
            false
        }
    }

    /**
     * Remueve un producto del carrito de compras.
     *
     * @param cartItem El ítem del carrito a eliminar.
     * @return True si se elimina exitosamente, de lo contrario false.
     */
    suspend fun removeFromCart(cartItem: CartItem): Boolean {
        val response = retrofitCart.removeFromCart(getEmail(), cartItem.product.id_producto)
        println("Email: ${getEmail()},ID: ${cartItem.product.id_producto}")
        return if (response.isSuccessful) {
            true
        } else {
            false
        }
    }

    /**
     * Elimina el carrito de compras del usuario.
     *
     * @param email El correo electrónico del usuario.
     * @return True si se elimina exitosamente, de lo contrario false.
     */
    suspend fun deleteCart(email: String): Boolean {
        println("Email: $email")
        val response = retrofitCart.deleteCart(email)
        return if(response.isSuccessful) {
            true
        } else {
            false
        }
    }
}