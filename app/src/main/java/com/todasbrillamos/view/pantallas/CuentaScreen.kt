package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.utils.SharedPreferencesHelper
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.view.componentes.boton
import com.todasbrillamos.viewmodel.MainVM
import kotlinx.coroutines.launch

@Composable
fun CuentaScreen(navController: NavHostController, mainVM: MainVM,sharedPreferencesHelper: SharedPreferencesHelper) {
    // Definir gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4),
        Color(0xFFffbba8)
    )

    // Estado para los campos de texto
    val nombre = remember { mutableStateOf("") }
    val apellido = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // Estado para manejar errores
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    // Obtener el estado del usuario desde el ViewModel
    val userInfo by mainVM.userInfo.collectAsState() // Asegúrate de que userInfo es un StateFlow o LiveData

    // Actualizar los campos de texto cuando userInfo cambia
    LaunchedEffect(userInfo) {
        try{
            sharedPreferencesHelper.getEmail()?.let { mainVM.getLoggedUser(it) }
            userInfo?.let {
                nombre.value = it.nombre
                apellido.value = it.apellido
                email.value = it.correo_electronico
                telefono.value = it.telefono
                password.value = "" // Dejar el campo de contraseña vacío o no mostrarla por seguridad
            }
        }catch (e: Exception){
            errorMessage.value = "Ocurrió un error al cargar tus datos"
        }
    }

    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(innerPadding)
                .padding(28.dp)
        ) {
            CampoTexto(
                labelValue = "Nombre",
                painterResource = painterResource(id = R.drawable.profile),
                textValue = nombre.value,
                onValueChange = { nombre.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                labelValue = "Apellido",
                painterResource = painterResource(id = R.drawable.profile),
                textValue = apellido.value,
                onValueChange = { apellido.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                labelValue = "Correo electrónico",
                painterResource = painterResource(id = R.drawable.mail),
                textValue = email.value,
                onValueChange = { email.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                labelValue = "Teléfono",
                painterResource = painterResource(id = R.drawable.phone),
                textValue = telefono.value,
                onValueChange = { telefono.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                labelValue = "Contraseña",
                painterResource = painterResource(id = R.drawable.pass),
                textValue = password.value,
                onValueChange = { password.value = it }
            )
            Spacer(modifier = Modifier.height(32.dp))

            boton(value = "Actualizar datos") {
                scope.launch {
                    try {
                        val result = mainVM.updateUser(
                            email = email.value,
                            name = nombre.value,
                            lastName = apellido.value,
                            phone = telefono.value,
                            password = password.value
                        )

                        // Manejo del resultado
                        if (result != null) {
                            // Puedes agregar lógica adicional si es necesario
                        } else {
                            errorMessage.value = "Error al actualizar la información."
                        }
                    } catch (e: Exception) {
                        errorMessage.value = e.message ?: "Ocurrió un error inesperado."
                    }
                }
            }

            // Mostrar mensaje de error si existe
            errorMessage.value?.let {
                AlertDialog(
                    onDismissRequest = { errorMessage.value = null },
                    title = { Text("Error") },
                    text = { Text(it) },
                    confirmButton = {
                        TextButton(onClick = { errorMessage.value = null }) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}