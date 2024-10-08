package com.todasbrillamos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.todasbrillamos.model.RemoteConnecter
import com.todasbrillamos.model.data.CartItem
import com.todasbrillamos.model.data.ProductInfo
import com.todasbrillamos.model.data.SignupRequest
import com.todasbrillamos.model.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainVM : ViewModel() {
    private val connecter = RemoteConnecter()

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    private val _userCart = MutableStateFlow<List<CartItem>>(emptyList())
    val userCart: StateFlow<List<CartItem>> = _userCart

    private val _products = MutableStateFlow<List<ProductInfo>>(emptyList())
    val products: StateFlow<List<ProductInfo>> = _products

    // Obtener información del usuario por email
    suspend fun getUserByEmail(email: String) {
        val user = connecter.getUserbyEmail(email)
        _userInfo.value = user
    }

    // Actualizar la información del usuario
    suspend fun updateUserByEmail(newTelefono: String, newNombre: String, newApellido: String) {
        val currentUser = _userInfo.value

        if (currentUser != null) {
            val updatedUser = currentUser.copy(
                telefono = newTelefono,
                nombre = newNombre,
                apellido = newApellido
            )
            val result = connecter.updateUserByEmail(currentUser.correo_electronico, updatedUser)
            _userInfo.value = result
        } else {
            Log.e("MainVM", "No user information available to update.")
        }
    }


    // Actualizar direcciones del usuario
    suspend fun updateUserAddresses(newDirecciones: List<String>) {
        val currentUser = _userInfo.value
        if (currentUser != null) {
            val updatedUser = currentUser.copy(
                direcciones_envio = newDirecciones
            )

            val response = connecter.updateUserByEmail(currentUser.correo_electronico, updatedUser)

            if (response != null) {
                _userInfo.value = response
            } else {
                Log.e("MainVM", "Failed to update user info.")
            }
        } else {
            Log.e("MainVM", "No user information available to update.")
        }
    }

    // Función para iniciar sesión
    suspend fun signIn(email: String, password: String): String? {
        return connecter.signinUser(email, password).toString()
    }

    // Función para registrarse
    suspend fun signUp(signupRequest: SignupRequest): String? {
        return connecter.signupUser(
            signupRequest.correo_electronico,
            signupRequest.nombre,
            signupRequest.apellido,
            signupRequest.telefono,
            signupRequest.contrasena
        )
    }

    // Crear un PaymentIntent para el proceso de pago (con Stripe)
    /*suspend fun createPaymentIntent(amount: Int, currency: String): String? {
        // Aquí debería ir la lógica de conectar con tu backend para crear un PaymentIntent
        return connecter.createPaymentIntent(amount, currency)
    }*/
    // Obtener productos desde el backend
    suspend fun fetchProducts() {
        val productList = connecter.getProducts()
        if (productList != null) {
            _products.value = productList
            Log.d("MainVM", "Fetched products: ${productList.size}")
        } else {
            Log.e("MainVM", "Failed to fetch products.")
        }
    }

    suspend fun addToCartBack(cartItem: CartItem, quantity: Int = 1): Boolean {
        return connecter.addToCart(cartItem, quantity)
    }

    suspend fun removeFromCartBack(cartItem: CartItem): Boolean {
        return connecter.removeFromCart(cartItem)
    }

    fun setEmail(email: String){
        connecter.userEmail = email
    }


    /*

    Funciones del carrito

     */


    fun addToCart(product: ProductInfo) {
        val currentCart = _userCart.value.toMutableList()

        // Buscar si el producto ya está en el carrito
        val existingItem = currentCart.find { it.product.id_producto == product.id_producto }

        if (existingItem != null) {
            // Si ya está en el carrito, incrementar la cantidad
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            val updatedCart = currentCart.map {
                if (it.product.id_producto == product.id_producto) updatedItem else it
            }
            _userCart.value = updatedCart
        } else {
            // Si no está en el carrito, agregarlo con cantidad 1
            val newItem = CartItem(product, quantity = 1)
            currentCart.add(newItem)
            _userCart.value = currentCart
        }
    }

    fun removeFromCart(product: ProductInfo) {
        val currentCart = _userCart.value.toMutableList()

        val itemToRemove = currentCart.find { it.product.id_producto == product.id_producto }

        if (itemToRemove != null) {
            // Si la cantidad es 1, eliminar el elemento del carrito
            if (itemToRemove.quantity == 1) {
                currentCart.remove(itemToRemove)
            } else {
                // Si la cantidad es mayor a 1, decrementar la cantidad
                val updatedItem = itemToRemove.copy(quantity = itemToRemove.quantity - 1)
                // Replace the item in the cart
                currentCart[currentCart.indexOf(itemToRemove)] = updatedItem
            }
            // Update the cart state
            _userCart.value = currentCart
        }
    }

    fun calculateTotal(): Float {
        var total = 0f

        for (cartItem in _userCart.value) {
            total += cartItem.product.precio * cartItem.quantity
        }

        return total
    }

    suspend fun emptyCart(): Boolean {
        _userCart.value = emptyList()
        try{
            connecter.deleteCart(connecter.getEmail())
            return true
        }catch (e: Exception){
            return false
        }
    }

}
