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

class RemoteConnecter {

    var userEmail: String = ""

    private val retrofitClient by lazy {
        Retrofit.Builder()
            .baseUrl("http://98.82.104.24:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

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

    suspend fun getUserByEmail(email: String): UserInfo? {
        val response = retrofitUsers.getUserByEmail(email)
        return if (response.isSuccessful) {
            response.body()
        } else {
            when (response.code()) {
                404 -> UserInfo("", "", "", "")// User not found
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

    suspend fun updateUserInfo(dataChangeRequest: DataChangeRequest): Response<UserInfo> {
        return retrofitUsers.updateUser(dataChangeRequest.correo_electronico, dataChangeRequest)
    }


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
                    null
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



    suspend fun createPaymentIntent(paymentRequest: PaymentRequest): String? {
        return try {
            println("Connecter: $paymentRequest")
            val response = retrofitStripe.createPaymentIntent(paymentRequest)
            if (response.isSuccessful) {
                response.body()?.clientSecret  // Return the clientSecret for the PaymentIntent
            } else {
                Log.e("RemoteConnecter", "Failed to create Payment Intent: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("RemoteConnecter", "Error: ${e.message}")
            null
        }
    }

    fun getEmail(): String {
        return userEmail
    }

    fun setEmail(email: String) {
        userEmail = email
    }

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

    suspend fun removeFromCart(cartItem: CartItem): Boolean {
        val response = retrofitCart.removeFromCart(getEmail(), cartItem.product.id_producto)
        println("Email: ${getEmail()},ID: ${cartItem.product.id_producto}")
        return if (response.isSuccessful) {
            true
        } else {
            false
        }
    }


    suspend fun deleteCart(email: String): Boolean {
        println("Email: $email")
        val response = retrofitCart.deleteCart(email)
        return if(response.isSuccessful){
            true
        }else{
            false
        }
    }
}