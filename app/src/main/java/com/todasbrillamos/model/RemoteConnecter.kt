package com.todasbrillamos.model

import android.util.Log
import com.todasbrillamos.model.data.Auth
import com.todasbrillamos.model.data.CatalogAPI
import com.todasbrillamos.model.data.CatalogInfo
import com.todasbrillamos.model.data.PaymentRequest
import com.todasbrillamos.model.data.ProductAPI
import com.todasbrillamos.model.data.ProductInfo
import com.todasbrillamos.model.data.SignupRequest
import com.todasbrillamos.model.data.StripeAPI
import com.todasbrillamos.model.data.TokenResponse
import com.todasbrillamos.model.data.UserAPI
import com.todasbrillamos.model.data.UserInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteConnecter {
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

    suspend fun getUserbyEmail(email: String): UserInfo? {
        val response = retrofitUsers.getUserByEmail(email)
        return if(response.isSuccessful) {
            response.body()
        } else {
            when(response.code()) {
                404 -> UserInfo("", "", "", "", emptyList(), emptyList()) // User not found
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

    suspend fun updateUserByEmail(email: String, user: UserInfo): UserInfo? {
        val response = retrofitUsers.updateUserByEmail(email, user)
        return if(response.isSuccessful) {
            response.body()
        } else {
            when(response.code()) {
                404 -> {
                    Log.e("RemoteConnecter", "User not found")
                    null
                }
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

    suspend fun getProducts(): List<ProductInfo>? {
        val response = retrofitProducts.getProducts()
        return if(response.isSuccessful) {
            response.body()
        } else {
            when(response.code()) {
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
        return if(response.isSuccessful) {
            response.body()
        } else {
            when(response.code()){
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

    suspend fun updateProductById(id: Int, product: ProductInfo): ProductInfo? {
        val response = retrofitProducts.updateProductById(id, product)
        return if(response.isSuccessful){
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

    suspend fun signupUser(email: String, name: String, lastName: String, phone: String, password: String): String? {
        val requestBody = SignupRequest(email, name, lastName, phone, password)

        val response = retrofitAuth.signup(requestBody)

        return if (response.isSuccessful) {
            "User created successfully"
        }else {
            when(response.code()) {
                400 -> {
                    Log.e("RemoteConnecter", "Bad request")
                    null
                }
                else -> {
                    Log.e("RemoteConnecter", "Error: ${response.code()}")
                    null
                }
            }
        }
    }

    suspend fun signinUser(email: String, password: String): TokenResponse? {
        val response = retrofitAuth.signin(email, password)
        return if (response.isSuccessful) {
            response.body()
        }else{
            Log.e("RemoteConnecter", "Error: ${response.code()}")
            null
        }
    }

    suspend fun getCatalogById(catalogo_id: Int): CatalogInfo? {
        val response = retrofitCatalogs.getCatalogById(catalogo_id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            Log.e("RemoteConnecter", "Error: ${response.code()}")
            null
        }
    }

    suspend fun createPaymentIntent(paymentRequest: String): String? {
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



}