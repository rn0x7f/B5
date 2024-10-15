package com.todasbrillamos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.todasbrillamos.model.RemoteConnecter
import com.todasbrillamos.model.data.CartItem
import com.todasbrillamos.model.data.Compras
import com.todasbrillamos.model.data.DataChangeRequest
import com.todasbrillamos.model.data.Direcciones
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
    suspend fun getUserByEmail() {
        val email = connecter.getEmail()
        val user = connecter.getUserByEmail(email)
        _userInfo.value = user
    }

    suspend fun getLoggedUser() {
        val user = connecter.getUserByEmail(getEmail())
        _userInfo.value = user
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

    suspend fun updateUser(email: String, name: String, lastName: String, phone: String, password: String): UserInfo? {
        // Obtener la información actual del usuario
        val currentUserInfo = connecter.getUserByEmail(getEmail())


        // Verificar si la información actual del usuario está disponible
        currentUserInfo?.let {
            // Crear el objeto DataChangeRequest con los nuevos datos
            val dataChangeRequest = DataChangeRequest(
                correo_electronico = email,
                nombre = name,
                apellido = lastName,
                telefono = phone,
                contrasena = password
            )

            // Llamar a la función para actualizar la información del usuario en el RemoteConnecter
            val response = connecter.updateUserInfo(dataChangeRequest)

            // Verificar si la respuesta es exitosa
            return if (response.isSuccessful) {
                // Actualizar el estado local _userInfo con la nueva información del usuario
                _userInfo.value =
                    UserInfo(
                        correo_electronico = email,
                        nombre = name,
                        apellido = lastName,
                        telefono = phone,
                        direcciones_envio = userInfo.value?.direcciones_envio ?: Direcciones(emptyList()),
                        compras = userInfo.value?.compras ?: Compras(emptyList())
                    )
                connecter.setEmail(email)
                response.body()
            } else {
                // Registrar el error si falla la actualización
                Log.e("MainVM", "Error al actualizar el usuario: ${response.errorBody()?.string()}")
                null
            }
        }

        return null
    }

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

    fun getEmail(): String{
        return connecter.userEmail
    }


    /*

    Funciones del carrito

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
