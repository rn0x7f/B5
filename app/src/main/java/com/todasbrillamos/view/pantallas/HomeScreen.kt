package com.todasbrillamos.view.pantallas


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.todasbrillamos.view.componentes.ImageGrid
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.view.componentes.Pager
import com.todasbrillamos.view.componentes.Product

/**
 * Pantalla de inicio de la aplicación.
 * El usuario ve esta pantalla al abrir la aplicación.
 */

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    // Definir el gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    val uriHandler = LocalUriHandler.current

    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally // Centrar los elementos
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo zazil",
                    modifier = Modifier.size(150.dp) // Ajusta el tamaño de la imagen
                )
            }

            item {
                Pager(
                    images = listOf(
                        R.drawable.imgprueba,
                        R.drawable.foto_acerca_de
                    )
                )
            }
            item {
                ImageGrid(
                    products = listOf(
                        Product(
                            imageRes = R.drawable.imgprueba,
                            name = "Producto 1",
                            price = "$19.99"
                        ),
                        Product(
                            imageRes = R.drawable.imgprueba,
                            name = "Producto 2",
                            price = "$29.99"
                        ),
                        Product(
                            imageRes = R.drawable.imgprueba,
                            name = "Producto 3",
                            price = "$39.99"
                        ),
                        Product(
                            imageRes = R.drawable.imgprueba,
                            name = "Producto 4",
                            price = "$49.99"
                        ),
                        Product(
                            imageRes = R.drawable.imgprueba,
                            name = "Producto 5",
                            price = "$59.99"
                        ),
                        Product(
                            imageRes = R.drawable.imgprueba,
                            name = "Producto 6",
                            price = "$69.99"
                        ),
                        Product(
                            imageRes = R.drawable.imgprueba,
                            name = "Producto 7",
                            price = "$79.99"
                        ),
                        Product(
                            imageRes = R.drawable.imgprueba,
                            name = "Producto 8",
                            price = "$89.99"
                        )
                    ),
                    columns = 2 // Definimos el número de columnas
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    // You can create a dummy NavController for the preview
    val navController = rememberNavController()
    HomeScreen(navController)
}
