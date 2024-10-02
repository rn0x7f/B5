package com.todasbrillamos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.todasbrillamos.model.RemoteConnecter
import com.todasbrillamos.model.data.SignupRequest
import com.todasbrillamos.model.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainVM : ViewModel() {
    private val connecter = RemoteConnecter()

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    suspend fun getUserByEmail(email: String) {
        val user = connecter.getUserbyEmail(email)
        _userInfo.value = user
    }

    suspend fun updateUserByEmail(newTelefono: String , newNombre: String, newApellido: String) {
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

    suspend fun updateUsersCart(newCompras: List<String>) {
        val currentUser = _userInfo.value
        if (currentUser != null) {
            val updatedUser = currentUser.copy(
                compras = newCompras
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

    suspend fun updateUserAddresses(newDirecciones: List<String>){
        val currentUser = _userInfo.value
        if(currentUser != null){
            val updatedUser = currentUser.copy(
                direcciones_envio = newDirecciones
            )

            val response = connecter.updateUserByEmail(currentUser.correo_electronico, updatedUser)

            if(response != null){
                _userInfo.value = response
            } else {
                Log.e("MainVM", "Failed to update user info.")

            }
        }else{
            Log.e("MainVM", "No user information available to update.")
        }
    }

    suspend fun signIn(email: String, password: String): String? {
        return connecter.signinUser(email, password).toString()
    }

    suspend fun signUp(signupRequest: SignupRequest): String? {
        return connecter.signupUser(signupRequest.correo_electronico, signupRequest.nombre, signupRequest.apellido, signupRequest.telefono, signupRequest.contrasena)
    }
}