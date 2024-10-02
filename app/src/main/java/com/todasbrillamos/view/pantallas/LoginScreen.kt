package com.todasbrillamos.view.pantallas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Composable
fun LoginScreen(navController: NavController, mainVM: MainVM) {
    val coroutineScope = rememberCoroutineScope()
    val sharedPreferencesHelper = SharedPreferencesHelper(LocalContext.current)
    val statusMessage = remember { mutableStateOf("") }

    // Definir el gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors))
            .padding(start = 28.dp, end = 28.dp, top = 45.dp)
    ) {
        TextoNormal(value = "Hola!")
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

        // Define state variables to hold user input
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
        TextoClickeableOlvideContrasena()
        Spacer(modifier = Modifier.padding(20.dp))
        boton(value = "Iniciar Sesion") {
            coroutineScope.launch {
                try {
                    val result = mainVM.signIn(email.value, password.value)

                    if (result != null) {
                        // Save the token to SharedPreferences
                        sharedPreferencesHelper.saveToken(result)

                        // Navigate to the home screen
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        statusMessage.value =
                            "Error de inicio de sesión. Verifica tus credenciales."
                    }
                } catch (e: Exception) {
                    Log.e("LoginScreen", "Error de inicio de sesión", e)
                    statusMessage.value = "Error de inicio de sesión. Inténta de nuevo más tarde."
                }
            }
        }

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
    LoginScreen(navController, MainVM())
}