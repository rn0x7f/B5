package com.todasbrillamos.view.pantallas


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.Espaciador
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.view.componentes.Pager
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.TextoResaltadoMini

/**
 * Pantalla de Acerca de Nosotros.
 * Describe las características y el propósito de la app.
 * @author Roger Rendón
 * @author Kevin Castro
 */

@Composable
fun AcercaScreen(navController: NavHostController) {

    // Define gradient
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Initial color
        Color(0xFFffbba8)  // Final color
    )

    val uriHandler = LocalUriHandler.current

    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(innerPadding)
                .padding(28.dp)
        ) {
            item {
                TextoResaltado(value = "Acerca de nosotras: ")
                Pager(
                    images = listOf(
                        R.drawable.imgprueba,
                        R.drawable.foto_acerca_de
                    )
                )
                Espaciador()
            }
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextoResaltado(value = "Preguntas Frecuentes: ")
                    TextoNormal(value = "Lorem ipsum", size = 16)
                }
            }
            item {
                TextoResaltadoMini(value = "Contáctanos: ")
                Column(modifier = Modifier.padding(18.dp)) {
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
                    Row(modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.facebook.com/")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "correo",
                            modifier = Modifier
                                .size(24.dp)
                                .weight(2f)
                        )
                        Text(text = "@todasbrillamos", modifier = Modifier.weight(8f))
                    }
                    Espaciador()
                    Row(modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.instagram.com/")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.instagram),
                            contentDescription = "correo",
                            modifier = Modifier
                                .size(24.dp)
                                .weight(2f)
                        )
                        Text(text = "@todasbrillamos", modifier = Modifier.weight(8f))
                    }
                    Espaciador()
                    Row(modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.twitter.com/")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.twitter),
                            contentDescription = "correo",
                            modifier = Modifier
                                .size(24.dp)
                                .weight(2f)
                        )
                        Text(text = "@todasbrillamos", modifier = Modifier.weight(8f))
                    }
                    Espaciador()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAcercaScreen() {
    // You can create a dummy NavController for the preview
    val navController = rememberNavController()
    AcercaScreen(navController)
}
