@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.todasbrillamos.view.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todasbrillamos.R

@Composable
fun TextoNormal(value:String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        )
        ,color = colorResource(id = R.color.negroPrincipal),
        textAlign = TextAlign.Center

    )
}

@Composable
fun TextoResaltado(value:String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        )
        ,color = colorResource(id = R.color.rosaTB),
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
fun CampoTexto(labelValue: String, painterResource: Painter) {

    val textValue = remember{
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.rosaTB),
            focusedLabelColor = colorResource(id = R.color.rosaTB),
            cursorColor = colorResource(id = R.color.rosaTB),
            containerColor = colorResource(id = R.color.BG)
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
        },
        singleLine = true, // Asegura que sea una sola línea
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        }
    )
}

@Composable
fun CampoPassword(labelValue: String, painterResource: Painter) {

    val password = remember{
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.rosaTB),
            focusedLabelColor = colorResource(id = R.color.rosaTB),
            cursorColor = colorResource(id = R.color.rosaTB),
            containerColor = colorResource(id = R.color.BG)
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            password.value = it
        },
        singleLine = true, // Asegura que sea una sola línea
        leadingIcon = {
            Icon(painter = painterResource, contentDescription ="" )
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            var description = if(passwordVisible.value){
                "esconder contraseña"
            }else {
                "mostrar contraseña"
            }

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else
            PasswordVisualTransformation()
    )
}

@Composable
fun CheckboxComp(value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val checkedState = remember {
            mutableStateOf(false)
        }
        androidx.compose.material3.Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
            }
        )
        TextoClickeable(value = value)
    }
}

@Composable
fun boton(value: String) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            colorResource(id = R.color.rosaTB), // Asegúrate de definir estos colores
                            colorResource(id = R.color.rosaTB)
                        )
                    ),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp, // Cambiado a sp
                fontWeight = FontWeight.Bold
            )
        }
    }
}
