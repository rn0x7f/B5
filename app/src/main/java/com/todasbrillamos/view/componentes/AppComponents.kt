@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

package com.todasbrillamos.view.componentes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
fun TextoResaltadoMediano(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 24.sp,
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
fun TextoClickeable(value: String, onTermsClick: () -> Unit) {
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
                    onTermsClick() // Llama a la función para mostrar el diálogo
                }
        }
    )
}

@Composable
fun TerminosDialog(onDismiss: () -> Unit) {
    // Se usa un Column para permitir el desplazamiento
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Aviso de Privacidad de Zazil") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text("AVISO DE PRIVACIDAD\n" +
                        "\n" +
                        "En Fundación Todas Brillamos AC, valoramos la privacidad de nuestros clientes y nos comprometemos a proteger la información personal que nos proporcionan. Esta política de privacidad explica cómo recopilamos, utilizamos y protegemos sus datos personales.\n" +
                        "\n" +
                        "INFORMACIÓN RECOLECTADA\n" +
                        "\n" +
                        "- Datos personales: nombre, dirección, correo electrónico, número de teléfono\n" +
                        "- Información de pago: tarjeta de crédito, débito o PayPal\n" +
                        "\n" +
                        "USO DE LA INFORMACIÓN\n" +
                        "\n" +
                        "- Procesar y enviar pedidos\n" +
                        "- Enviar correos electrónicos con promociones y ofertas especiales\n" +
                        "- Mejorar nuestra tienda online y experiencia de usuario\n" +
                        "\n" +
                        "PROTECCIÓN DE LA INFORMACIÓN\n" +
                        "\n" +
                        "- Utilizamos medidas de seguridad para proteger sus datos personales\n" +
                        "- No compartimos información personal con terceros, excepto para procesar pedidos y envíos\n" +
                        "\n" +
                        "DERECHOS DE LOS CLIENTES\n" +
                        "\n" +
                        "- Acceder, rectificar o cancelar su información personal en cualquier momento\n" +
                        "- Oponerse al uso de su información para fines de marketing\n" +
                        "\n" +
                        "CAMBIOS EN LA POLÍTICA DE PRIVACIDAD\n" +
                        "\n" +
                        "- Podemos actualizar esta política de privacidad en cualquier momento\n" +
                        "- Se notificará a los clientes de cualquier cambio significativo\n" +
                        "\n" +
                        "FECHA DE ÚLTIMA ACTUALIZACIÓN: 2 de Septiembre 2024\n" +
                        "\n" +
                        "Si tienes alguna pregunta o inquietud, por favor no dudes en contactarnos.")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Aceptar")
            }
        }
    )
}
@Composable
fun TextoClickeableLogin(navController: NavController) {
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
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true } // Eliminar "signup" de la pila
                    }
                }
        }
    )
}

@Composable
fun TextoClickeableRegistro(navController: NavController) {
    val textoInicial = "¿Aún no tienes cuenta? "
    val registrate = "Registrarme"
    val registroTag = "registro"

    
    val annString = buildAnnotatedString {
        append(textoInicial)
        pushStringAnnotation(tag = registroTag, annotation = registroTag)
        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.rosaTB),
                textDecoration = TextDecoration.Underline
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
                    navController.navigate("signup"){
                        popUpTo("login") { inclusive = true } // Eliminar "login" de la pila
                    }
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


