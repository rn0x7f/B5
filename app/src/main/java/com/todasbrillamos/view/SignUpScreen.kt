package com.todasbrillamos.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.CampoPassword
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado

@Composable
fun SignUpScreen(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.blancoFondo))
            .padding(28.dp)
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.blancoFondo))
        ) {
            TextoNormal(value = stringResource(id = R.string.Bienvenida))
            TextoResaltado(value = stringResource(id = R.string.CrearCuenta))
            Spacer(modifier = Modifier.height(20.dp))
            CampoTexto(
                labelValue = "Nombre",
                painterResource = painterResource(id = R.drawable.profile)
            )
            CampoTexto(
                labelValue = "Apellido",
                painterResource = painterResource(id = R.drawable.profile)
            )
            CampoTexto(
                labelValue = "Correo electronico",
                painterResource(id = R.drawable.mail)
            )
            CampoTexto(
                labelValue = "Telefono",
                painterResource = painterResource(id = R.drawable.phone)
            )
            CampoPassword(
                labelValue = "Contraseña",
                painterResource = painterResource (id = R.drawable.pass))



        }
    }
}

@Preview
@Composable
fun PreviewSignUp(){
    SignUpScreen()
}
