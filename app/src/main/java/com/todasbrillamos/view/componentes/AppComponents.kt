@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.todasbrillamos.view.componentes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todasbrillamos.R

@Composable
fun TextoNormal(value: String, size: Int = 24) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.negroPrincipal),
        textAlign = TextAlign.Center

    )
}

@Composable
fun TextoResaltado(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.rosaTB),
        textAlign = TextAlign.Center

    )
}

@Composable
fun TextoResaltadoMini(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ), color = colorResource(id = R.color.rosaTB),
        textAlign = TextAlign.Center

    )
}

@Composable
fun TextoClickeable(value: String) {
    val textoInicial = "Al continuar aceptas nuestros "
    val TerminosCondiciones = "Términos y Condiciones"
    val politica = "terminosCondiciones"

    val annString = buildAnnotatedString {
        append(textoInicial)
        pushStringAnnotation(tag = politica, annotation = politica) // Añadir la anotación
        withStyle(style = SpanStyle(color = colorResource(id = R.color.rosaTB))) {
            append(TerminosCondiciones) // Aplicar estilo al texto
        }
        pop() // Cerrar la anotación
    }

    ClickableText(
        text = annString,
        onClick = { offset ->
            annString.getStringAnnotations(tag = politica, start = offset, end = offset)
                .firstOrNull()?.let {
                    // Aquí podrías manejar el clic para abrir los términos y condiciones
                }
        }
    )
}

@Composable
fun TextoClickeableLogin(value: String) {
    val textoInicial = "¿Ya tienes una cuenta? "
    val sesion = "Inicia Sesión"
    val login = "login"

    val annString = buildAnnotatedString {
        append(textoInicial)
        pushStringAnnotation(tag = login, annotation = login) // Añadir la anotación
        withStyle(style = SpanStyle(color = colorResource(id = R.color.rosaTB))) {
            append(sesion) // Aplicar estilo al texto
        }
        pop() // Cerrar la anotación
    }

    ClickableText(
        text = annString,
        modifier = Modifier
            .fillMaxWidth() // Rellenar el ancho máximo
            .padding(16.dp), // Añadir padding
        style = TextStyle(
            textAlign = TextAlign.Center // Centrar el texto
        ),
        onClick = { offset ->
            annString.getStringAnnotations(tag = login, start = offset, end = offset)
                .firstOrNull()?.let {
                    // Aquí podrías manejar el clic para INICIAR SESIÓN
                }
        }
    )
}

@Composable
fun TextoClickeableRegistro() {
    val textoInicial = "¿Aún no tienes cuenta? "
    val registrate = "Registrarme"
    val registroTag = "registro"

    
    val annString = buildAnnotatedString {
        append(textoInicial)
        pushStringAnnotation(tag = registroTag, annotation = registroTag)
        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.rosaTB),
                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
            )
        ) {
            append(registrate)
        }
        pop()
    }

    ClickableText(
        text = annString,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = TextStyle(
            textAlign = TextAlign.Center
        ),
        onClick = { offset ->
            annString.getStringAnnotations(tag = registroTag, start = offset, end = offset)
                .firstOrNull()?.let {
                }
        }
    )
}


@Composable
fun TextoClickeableOlvideContrasena() {
    val textoOlvide = "Olvidé mi contraseña"
    val olvideTag = "olvideContrasena"

    val annString = buildAnnotatedString {
        pushStringAnnotation(tag = olvideTag, annotation = olvideTag)
        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.rosaTB),
                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
            )
        ) {
            append(textoOlvide)
        }
        pop()
    }

    ClickableText(
        text = annString,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = TextStyle(
            textAlign = TextAlign.Center // Centrar el texto
        ),
        onClick = { offset ->
            annString.getStringAnnotations(tag = olvideTag, start = offset, end = offset)
                .firstOrNull()?.let {
                    // Aquí podrías manejar el clic para OLVIDAR CONTRASEÑA
                }
        }
    )
}

@Composable
fun Espaciador() {
    Spacer(modifier = Modifier.padding(8.dp))
}


