package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.CampoPassword
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.TextoClickeableOlvideContrasena
import com.todasbrillamos.view.componentes.TextoClickeableRegistro
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.boton

@Composable
fun LoginScreen() {
    // Definir el gradiente
    val gradientColors = listOf(
        Color( 0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors))
            .padding(28.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
        ) {
            TextoNormal(value = "Hola!")
            TextoResaltado(value = "Bienvenida de vuelta")
            Spacer(modifier = Modifier
                .padding(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Imagen de bienvenida",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(vertical = 10.dp)
            )

            CampoTexto(
                labelValue = "Correo electronico",
                painterResource(id = R.drawable.mail)
            )
            CampoPassword(
                labelValue = "Contraseña",
                painterResource = painterResource (id = R.drawable.pass)
            )
            TextoClickeableOlvideContrasena()
            Spacer(modifier = Modifier
                .padding(20.dp))
            boton(value = "Iniciar Sesion")
            TextoClickeableRegistro()

        }


    }
}

@Preview
@Composable
fun PreviewLogin(){
    LoginScreen()
}