package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.model.data.SignupRequest
import com.todasbrillamos.view.componentes.CampoPassword
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.CheckboxComp
import com.todasbrillamos.view.componentes.TextoClickeableLogin
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.boton
import com.todasbrillamos.viewmodel.MainVM
import kotlinx.coroutines.launch

/**
 * Pantalla de registro de usuarios
 * @author Roger Rendón
 * @author Kevin Castro
 * @param navController Controlador de navegación para la pantalla de registro
 * @param mainVM ViewModel principal para la lógica de la aplicación
 */

@Composable
fun SignUpScreen(navController: NavController, mainVM: MainVM) {
    val coroutineScope = rememberCoroutineScope()
    val statusMessage = remember { mutableStateOf("") }

    val nombre = remember { mutableStateOf("") }
    val apellido = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")
    val passwordRegex = Regex("^(?=.*[A-Z])[A-Za-z]{8,}\$")
    val phoneRegex = Regex("^\\+?52?\\s?\\(?\\d{2}\\)?\\s?\\d{4}\\s?\\d{4}\$")

    // Definir el gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors))
            .padding(start = 28.dp, end = 28.dp, top = 45.dp, bottom = 45.dp)
    ) {
        TextoNormal(value = "Hola!")
        TextoResaltado(value = stringResource(id = R.string.CrearCuenta))
        Spacer(modifier = Modifier.height(20.dp))

        // Campo de Nombre
        CampoTexto(
            labelValue = "Nombre",
            painterResource = painterResource(id = R.drawable.profile),
            textValue = nombre.value,
            onValueChange = {
                nombre.value = it
                statusMessage.value = "" // Limpiar el mensaje al cambiar el nombre
            }
        )

        // Campo de Apellido
        CampoTexto(
            labelValue = "Apellido",
            painterResource = painterResource(id = R.drawable.profile),
            textValue = apellido.value,
            onValueChange = {
                apellido.value = it
                statusMessage.value = "" // Limpiar el mensaje al cambiar el apellido
            }
        )

        // Campo de Correo Electrónico
        CampoTexto(
            labelValue = "Correo electrónico",
            painterResource = painterResource(id = R.drawable.mail),
            textValue = email.value,
            onValueChange = { input ->
                email.value = input
                // Validar el correo mientras se escribe
                statusMessage.value = if (!emailRegex.matches(email.value) && email.value.isNotEmpty()) {
                    "Correo electrónico inválido"
                } else {
                    "" // Limpiar mensaje si es válido
                }
            }
        )

        // Campo de Teléfono
        CampoTexto(
            labelValue = "Teléfono",
            painterResource = painterResource(id = R.drawable.phone),
            textValue = telefono.value,
            onValueChange = { input ->
                telefono.value = input
                // Validar el teléfono mientras se escribe
                statusMessage.value = if (!phoneRegex.matches(telefono.value) && telefono.value.isNotEmpty()) {
                    "Teléfono inválido. Formato válido: 55 1234 5678"
                } else {
                    "" // Limpiar mensaje si es válido
                }
            }
        )

        // Campo de Contraseña
        CampoPassword(
            labelValue = "Contraseña",
            painterResource = painterResource(id = R.drawable.pass),
            password = password.value,
            onValueChange = { input ->
                password.value = input
                // Validar la contraseña mientras se escribe
                statusMessage.value = if (!passwordRegex.matches(password.value) && password.value.isNotEmpty()) {
                    "La contraseña debe tener al menos 8 letras y contener al menos una mayúscula."
                } else {
                    "" // Limpiar mensaje si es válido
                }
            }
        )

        // Checkbox de términos y condiciones
        CheckboxComp(value = "Al crear una cuenta, aceptas nuestros términos y condiciones")

        Spacer(modifier = Modifier.height(10.dp))

        // Mensaje de estado
        Text(text = statusMessage.value, color = Color.Red)

        Spacer(modifier = Modifier.height(190.dp))

        // Botón de registro
        boton(value = "Registrar") {
            // Verifica si hay algún mensaje de error antes de proceder
            if (statusMessage.value.isEmpty()) {
                // Crear un objeto de solicitud con los datos del usuario
                val signupRequest = SignupRequest(
                    nombre = nombre.value,
                    apellido = apellido.value,
                    correo_electronico = email.value,
                    telefono = telefono.value,
                    contrasena = password.value
                )

                // Mandar los datos a la API usando el signup request
                coroutineScope.launch {
                    try {
                        // Llamar a la función del ViewModel para registrar al usuario
                        val result = mainVM.signUp(signupRequest)

                        if (result != null) {
                            // Navegar a la pantalla de inicio
                            navController.navigate("home") {
                                popUpTo("signup") { inclusive = true }
                            }
                        } else {
                            // Manejar el caso en que el resultado sea nulo (error en la API)
                            statusMessage.value = "Error al registrar. Verifica tus datos."
                        }
                    } catch (e: Exception) {
                        // Manejar excepciones
                        statusMessage.value = "Error en la conexión. Intenta más tarde."
                    }
                }
            }
        }

        TextoClickeableLogin(navController)
    }
}
@Preview
@Composable
fun PreviewSignUpScreen() {
    // Crear un NavController ficticio para la vista previa
    val navController = rememberNavController()
    SignUpScreen(navController, MainVM())
}

