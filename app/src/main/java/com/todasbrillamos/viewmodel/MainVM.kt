package com.todasbrillamos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.todasbrillamos.model.RemoteConnecter
import com.todasbrillamos.model.data.CartItem
import com.todasbrillamos.model.data.DataChangeRequest
import com.todasbrillamos.model.data.ProductInfo
import com.todasbrillamos.model.data.SignupRequest
import com.todasbrillamos.model.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel para manejar la lógica de negocio de la aplicación.
 * Proporciona acceso a la información del usuario, productos y carrito de compras.
 */
class MainVM : ViewModel() {
    private val connecter = RemoteConnecter()

    // Estado para la información del usuario
    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    // Estado para el carrito del usuario
    private val _userCart = MutableStateFlow<List<CartItem>>(emptyList())
    val userCart: StateFlow<List<CartItem>> = _userCart

    // Estado para el historial del carrito del usuario
    private val _userCartHistory = MutableStateFlow<List<List<CartItem>>>(emptyList())
    val userCartHistory: StateFlow<List<List<CartItem>>> = _userCartHistory

    // Estado para la lista de productos
    private val _products = MutableStateFlow<List<ProductInfo>>(emptyList())
    val products: StateFlow<List<ProductInfo>> = _products

    /**
     * Obtiene la información del usuario por su email.
     */
    suspend fun getUserByEmail() {
        val email = connecter.getEmail()
        val user = connecter.getUserByEmail(email)
        _userInfo.value = user
    }

    /**
     * Obtiene la información del usuario logueado por su email.
     */
    suspend fun getLoggedUser(email: String) {
        val user = connecter.getUserByEmail(email)
        _userInfo.value = user
    }

    /**
     * Inicia sesión con el correo electrónico y la contraseña proporcionados.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return Token de autenticación o null si falla.
     */
    suspend fun signIn(email: String, password: String): String? {
        return connecter.signinUser(email, password)
    }

    /**
     * Registra un nuevo usuario con la información proporcionada.
     *
     * @param signupRequest Datos del nuevo usuario.
     * @return Mensaje de éxito o error.
     */
    suspend fun signUp(signupRequest: SignupRequest): String? {
        return connecter.signupUser(
            signupRequest.correo_electronico,
            signupRequest.nombre,
            signupRequest.apellido,
            signupRequest.telefono,
            signupRequest.contrasena
        )
    }

    /**
     * Actualiza la información del usuario con los nuevos datos proporcionados.
     *
     * @param email Correo electrónico actualizado.
     * @param name Nombre actualizado.
     * @param lastName Apellido actualizado.
     * @param phone Teléfono actualizado.
     * @param password Contraseña actualizada.
     * @return Información del usuario actualizado o null si falla.
     */
    suspend fun updateUser(email: String, name: String, lastName: String, phone: String, password: String): UserInfo? {
        // Obtener la información actual del usuario
        val currentUserInfo = connecter.getUserByEmail(getEmail())

        currentUserInfo?.let {
            val dataChangeRequest = DataChangeRequest(
                correo_electronico = email,
                nombre = name,
                apellido = lastName,
                telefono = phone,
                contrasena = password
            )

            val response = connecter.updateUserInfo(dataChangeRequest)

            return if (response.isSuccessful) {
                _userInfo.value =
                    UserInfo(
                        correo_electronico = email,
                        nombre = name,
                        apellido = lastName,
                        telefono = phone
                    )
                connecter.setEmail(email)
                response.body()
            } else {
                Log.e("MainVM", "Error al actualizar el usuario: ${response.errorBody()?.string()}")
                null
            }
        }

        return null
    }

    /**
     * Obtiene productos desde el backend.
     */
    suspend fun fetchProducts() {
        val productList = connecter.getProducts()
        if (productList != null) {
            _products.value = productList
            Log.d("MainVM", "Fetched products: ${productList.size}")
        } else {
            Log.e("MainVM", "Failed to fetch products.")
        }
    }

    /**
     * Agrega un artículo al carrito.
     *
     * @param cartItem Artículo que se va a agregar al carrito.
     * @param quantity Cantidad del artículo a agregar (por defecto 1).
     * @return true si se agregó exitosamente, false en caso contrario.
     */
    suspend fun addToCartBack(cartItem: CartItem, quantity: Int = 1): Boolean {
        return connecter.addToCart(cartItem, quantity)
    }

    /**
     * Remueve un artículo del carrito.
     *
     * @param cartItem Artículo que se va a remover del carrito.
     * @return true si se removió exitosamente, false en caso contrario.
     */
    suspend fun removeFromCartBack(cartItem: CartItem): Boolean {
        return connecter.removeFromCart(cartItem)
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email Correo electrónico del usuario.
     */
    fun setEmail(email: String){
        connecter.userEmail = email
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo electrónico del usuario.
     */
    fun getEmail(): String{
        return connecter.userEmail
    }

    /*
    Funciones del carrito
    */

    /**
     * Agrega un producto al carrito.
     *
     * @param product Producto que se va a agregar al carrito.
     * @return true si se agregó exitosamente, false en caso contrario.
     */
    fun addToCart(product: ProductInfo): Boolean {
        val currentCart = _userCart.value.toMutableList()

        val existingItem = currentCart.find { it.product.id_producto == product.id_producto }

        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            val updatedCart = currentCart.map {
                if (it.product.id_producto == product.id_producto) updatedItem else it
            }
            _userCart.value = updatedCart
            return true
        } else {
            val newItem = CartItem(product, quantity = 1)
            currentCart.add(newItem)
            _userCart.value = currentCart
            return true
        }
    }

    /**
     * Remueve un producto del carrito.
     *
     * @param product Producto que se va a remover del carrito.
     */
    fun removeFromCart(product: ProductInfo) {
        val currentCart = _userCart.value.toMutableList()

        val itemToRemove = currentCart.find { it.product.id_producto == product.id_producto }

        if (itemToRemove != null) {
            if (itemToRemove.quantity == 1) {
                currentCart.remove(itemToRemove)
            } else {
                val updatedItem = itemToRemove.copy(quantity = itemToRemove.quantity - 1)
                currentCart[currentCart.indexOf(itemToRemove)] = updatedItem
            }
            _userCart.value = currentCart
        }
    }

    /**
     * Calcula el total del carrito.
     *
     * @return Total del carrito como un Float.
     */
    fun calculateTotal(): Float {
        var total = 0f

        for (cartItem in _userCart.value) {
            total += cartItem.product.precio * cartItem.quantity
        }

        return total
    }

    /**
     * Vacía el carrito y elimina los artículos en el backend.
     *
     * @return true si el carrito se vació exitosamente, false en caso contrario.
     */
    suspend fun emptyCart(): Boolean {
        _userCart.value = emptyList()
        return try {
            connecter.deleteCart(connecter.getEmail())
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Agrega el contenido del carrito a la historia del carrito.
     */
    fun addCartToHistory() {
        if (_userCart.value.isNotEmpty()) {
            _userCartHistory.value = listOf(_userCart.value) + _userCartHistory.value
        }
    }

    /**
     * Limpia el historial del carrito.
     */
    fun clearCartHistory(){
        _userCartHistory.value = emptyList()
    }
}