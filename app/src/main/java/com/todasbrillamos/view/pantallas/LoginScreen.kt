package com.todasbrillamos.view.pantallas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.utils.SharedPreferencesHelper
import com.todasbrillamos.view.componentes.CampoPassword
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.TextoClickeableOlvideContrasena
import com.todasbrillamos.view.componentes.TextoClickeableRegistro
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.boton
import com.todasbrillamos.viewmodel.MainVM
import kotlinx.coroutines.launch

/**
 *
 */
@Composable
fun LoginScreen(
    navController: NavController,
    mainVM: MainVM,
    sharedPreferencesHelper: SharedPreferencesHelper
) {
    val coroutineScope = rememberCoroutineScope()
    val statusMessage = remember { mutableStateOf("") }

    // Define the gradient background
    val gradientColors = listOf(
        Color(0xFFffe5b4),
        Color(0xFFffbba8)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors))
            .padding(start = 28.dp, end = 28.dp, top = 45.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextoNormal(value = "¡Hola!")
        TextoResaltado(value = "Bienvenida de vuelta")
        Spacer(modifier = Modifier.padding(20.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Imagen de bienvenida",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(vertical = 10.dp)
        )

        // Define state variables for user input
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        CampoTexto(
            labelValue = "Correo electrónico",
            painterResource(id = R.drawable.mail),
            textValue = email.value,
            onValueChange = { email.value = it }
        )

        CampoPassword(
            labelValue = "Contraseña",
            painterResource = painterResource(id = R.drawable.pass),
            password = password.value,
            onValueChange = { password.value = it }
        )
        Spacer(modifier = Modifier.padding(20.dp))
        boton(value = "Iniciar Sesión") {
            when {
                email.value.isEmpty() -> statusMessage.value = "Por favor, ingresa tu correo electrónico."
                password.value.isEmpty() -> statusMessage.value = "Por favor, ingresa tu contraseña."
                else -> {
                    coroutineScope.launch {
                        try {
                            val result = mainVM.signIn(email.value, password.value)

                            if (result != null) {
                                // Login successful
                                sharedPreferencesHelper.saveToken(result)
                                mainVM.setEmail(email.value)
                                sharedPreferencesHelper.saveEmail(email.value)
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                // Login failed
                                statusMessage.value =
                                    "Error de inicio de sesión. Verifica tus credenciales."
                            }
                        } catch (e: Exception) {
                            Log.e("LoginScreen", "Error de inicio de sesión", e)
                            statusMessage.value =
                                "Error de inicio de sesión. Inténta de nuevo más tarde."
                        }
                    }
                }
            }
        }

        // Display status messages
        if (statusMessage.value.isNotEmpty()) {
            Text(
                text = statusMessage.value,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        TextoClickeableRegistro(navController)
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    val sharedPreferencesHelper = SharedPreferencesHelper(LocalContext.current)
    LoginScreen(navController, MainVM(), sharedPreferencesHelper)
}