package com.todasbrillamos.view.pantallas


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.Espaciador
import com.todasbrillamos.view.componentes.PagerAcercaDe
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.TextoResaltadoMini

@Preview
@Composable
fun AcercaScreen() {
    // Definir el gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    val uriHandler = LocalUriHandler.current

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
            TextoResaltado(value = "Acerca de nosotras: ")
            PagerAcercaDe(images = listOf(
                R.drawable.imgprueba,
                R.drawable.foto_acerca_de
                ))
            Espaciador()
            TextoResaltado(value = "Preguntas frecuentes: ")
            Espaciador()
            TextoNormal(value = "Lorem ipsum", size = 15)
            Espaciador()
            TextoResaltadoMini(value = "Contáctanos: ")
            Espaciador()
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.mail),
                    contentDescription = "correo",
                    modifier = Modifier
                        .size(24.dp)
                        .weight(2f)
                )
                Text(text = "Lorem ipsum", modifier = Modifier.weight(8f))
            }
            Espaciador()
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.phone),
                    contentDescription = "correo",
                    modifier = Modifier
                        .size(24.dp)
                        .weight(2f)
                )
                Text(text = "lorem ipsum", modifier = Modifier.weight(8f))
            }
            Espaciador()
            Row{

                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "correo",
                    modifier = Modifier
                        .size(24.dp)
                        .weight(2f)
                        .clickable {
                            uriHandler.openUri("https://www.facebook.com/")
                        }
                )
                Text(text = "@todasbrillamos", modifier = Modifier.weight(8f))
            }
            Espaciador()
            Row{
                Image(
                    painter = painterResource(id = R.drawable.instagram),
                    contentDescription = "correo",
                    modifier = Modifier
                        .size(24.dp)
                        .weight(2f)
                        .clickable {
                            uriHandler.openUri("https://www.instagram.com/")
                        }
                )
                Text(text = "@todasbrillamos", modifier = Modifier.weight(8f))
            }
            Espaciador()
            Row{

                Image(
                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = "correo",
                    modifier = Modifier
                        .size(24.dp)
                        .weight(2f)
                        .clickable {
                            uriHandler.openUri("https://www.twitter.com/")
                        }
                )
                Text(text = "@todasbrillamos", modifier = Modifier.weight(8f))
            }
            Espaciador()
            
        }
    }
}