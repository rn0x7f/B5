package com.todasbrillamos.view.pantallas


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.todasbrillamos.view.componentes.TextoResaltadoMediano
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
                        R.drawable.foto_acerca_de,
                        R.drawable.foto_acerca_de
                    )
                )
                Espaciador()
            }
            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextoResaltado(value = "Preguntas Frecuentes: ")
                }
                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre secciones
                TextoResaltadoMediano(value = "¿Quienes Somos?")
                TextoNormal(
                    value = "Zazil es una marca comprometida con el bienestar de las mujeres y el cuidado del medio ambiente. Su misión es proporcionar soluciones innovadoras y sostenibles para el período menstrual. ¿Cómo lo hacen? A través de la creación de toallas femeninas reutilizables.",
                    size = 16
                )
                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre secciones
                TextoResaltadoMediano(value = "Visión")
                TextoNormal(
                    value = "Imaginamos un mundo donde la menstruación no solo es sostenible para el planeta, sino también empoderadora para todas las mujeres. Queremos que cada elección consciente de Zazil contribuya a la creación de comunidades fuertes, mujeres empoderadas económicamente y un entorno más saludable y equitativo. Nuestra visión es que Zazil no sea solo un producto, sino una fuerza positiva que transforma la forma en que vivimos la menstruación, promoviendo el bienestar personal y global.",
                    size = 16
                )
                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre secciones
                TextoResaltadoMediano(value = "Misión")
                TextoNormal(
                    value = "En Zazil, no solo estamos redefiniendo la menstruación, sino también el impacto que tiene en la economía y el medio ambiente. Nuestra misión es empoderar a las mujeres a tomar decisiones informadas sobre su salud menstrual mientras generan un impacto positivo en su bienestar financiero y en el planeta.",
                    size = 16
                )
                Espaciador()
            }
            item {
                TextoResaltadoMini(value = "Contáctanos: ")
                Column(modifier = Modifier.padding(18.dp)) {
                    Espaciador()
                    Row(modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.facebook.com/FundacionTodasBrillamos/?locale=es_LA")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "facebook",
                            modifier = Modifier
                                .size(24.dp)
                                .weight(2f)
                        )
                        Text(text = "@todasbrillamos", modifier = Modifier.weight(8f))
                    }
                    Espaciador()
                    Row(modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.instagram.com/FundacionTodasBrillamos")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.instagram),
                            contentDescription = "instagram",
                            modifier = Modifier
                                .size(24.dp)
                                .weight(2f)
                        )
                        Text(text = "@todasbrillamos", modifier = Modifier.weight(8f))
                    }
                    Espaciador()
                    Row(modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.tiktok.com/@todas.brillamos")
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.tiktok),
                            contentDescription = "tiktok",
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
    val navController = rememberNavController()
    AcercaScreen(navController)
}
