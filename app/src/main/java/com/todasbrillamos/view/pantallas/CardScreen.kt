import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.CampoTexto
import com.todasbrillamos.view.componentes.CampoPassword
import com.todasbrillamos.view.componentes.boton

@Preview
@Composable
fun CardScreen() {
    // Definir gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    // Recordar los valores de los campos de texto
    val cardNumber = remember { mutableStateOf("") }
    val expiryDate = remember { mutableStateOf("") }
    val cvv = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = gradientColors))
            .padding(28.dp)
    ) {
        // Campo para el número de tarjeta
        CampoTexto(
            labelValue = "Número de Tarjeta",
            painterResource = painterResource(id = R.drawable.card), // Cambia este ícono por uno adecuado
            textValue = cardNumber.value,
            onValueChange = { cardNumber.value = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para la fecha de caducidad
        CampoTexto(
            labelValue = "Fecha de Caducidad (MM/AA)",
            painterResource = painterResource(id = R.drawable.date), // Cambia este ícono por uno adecuado
            textValue = expiryDate.value,
            onValueChange = { expiryDate.value = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para el CVV
        CampoPassword(
            labelValue = "CVV",
            painterResource = painterResource(id = R.drawable.cvv), // Cambia este ícono por uno adecuado
            password = cvv.value,
            onValueChange = { cvv.value = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Campo para el código postal
        CampoTexto(
            labelValue = "Código Postal",
            painterResource = painterResource(id = R.drawable.location), // Cambia este ícono por uno adecuado
            textValue = postalCode.value,
            onValueChange = { postalCode.value = it }
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Botón para continuar
        boton(value = "Pagar") {
            // Acción para realizar el pago con la tarjeta
        }
    }
}
