package com.todasbrillamos.view.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todasbrillamos.R

/**
 * Campo de texto personalizado con animaciones y estilos personalizados.
 *
 * @param labelValue El texto que se muestra como etiqueta del campo.
 * @param painterResource Icono que se muestra al inicio del campo.
 * @param textValue El valor actual del campo de texto.
 * @param onValueChange Callback que se llama cuando el valor del campo cambia.
 * @param modifier Modificador opcional para personalizar el campo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoTexto(
    labelValue: String,
    painterResource: Painter,
    textValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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
        value = textValue,
        onValueChange = { onValueChange(it) },
        singleLine = true,
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        }
    )
}

/**
 * Campo de texto para contraseñas con opción para mostrar/ocultar la contraseña.
 *
 * @param labelValue El texto que se muestra como etiqueta del campo de contraseña.
 * @param painterResource Icono que se muestra al inicio del campo.
 * @param password El valor actual del campo de contraseña.
 * @param onValueChange Callback que se llama cuando el valor del campo cambia.
 * @param modifier Modificador opcional para personalizar el campo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoPassword(
    labelValue: String,
    painterResource: Painter,
    password: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val passwordVisible = remember { mutableStateOf(false) }

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
        value = password,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (passwordVisible.value) {
                "esconder contraseña"
            } else {
                "mostrar contraseña"
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else
            PasswordVisualTransformation()
    )
}

/**
 * Componente de casilla de verificación con opción para mostrar un diálogo al hacer clic.
 *
 * @param value El texto que se muestra junto a la casilla de verificación.
 * @param onCheckedChange Callback que se llama cuando se cambia el estado de la casilla de verificación.
 */
@Composable
fun CheckboxComp(value: String, onCheckedChange: (Boolean) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val checkedState = remember { mutableStateOf(false) }

        androidx.compose.material3.Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onCheckedChange(it)  // Notificar al padre sobre el cambio de estado
            }
        )

        TextoClickeable(value = value) {
            showDialog = true
        }

        if (showDialog) {
            TerminosDialog(onDismiss = { showDialog = false })
        }
    }
}

/**
 * Botón personalizado con fondo degradado y texto centrado.
 *
 * @param value El texto que se mostrará en el botón.
 * @param onClick Callback que se llama al hacer clic en el botón.
 */
@Composable
fun boton(value: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
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
                            colorResource(id = R.color.rosaTB), // Colores del degradado
                            colorResource(id = R.color.rosaTB)
                        )
                    ),
                    shape = RoundedCornerShape(50.dp),
                ),
            contentAlignment = Alignment.Center // Centrar el contenido de la Box
        ) {
            Text(
                text = value,
                fontSize = 18.sp, // Tamaño de fuente
                fontWeight = FontWeight.Bold,
                style = TextStyle(textAlign = TextAlign.Center) // Centrar el texto
            )
        }
    }
}