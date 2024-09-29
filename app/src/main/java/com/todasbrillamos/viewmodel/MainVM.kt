package com.todasbrillamos.viewmodel

import androidx.lifecycle.ViewModel
import com.todasbrillamos.model.RemoteConnecter
import com.todasbrillamos.model.data.ProductInfo
import com.todasbrillamos.model.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainVM : ViewModel() {
    private val connecter = RemoteConnecter()

    private val _cartState = MutableStateFlow<List<ProductInfo>>(emptyList())
    val cartState: StateFlow<List<ProductInfo>> = _cartState

    private val _userState = MutableStateFlow<List<UserInfo>>(emptyList())
    val userState: StateFlow<List<UserInfo>> = _userState

    suspend fun getProducts() {
        TODO("Not yet implemented")
    }

    suspend fun getUsers() {
        TODO("Not yet implemented")
    }

}