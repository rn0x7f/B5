package com.todasbrillamos.view.pantallas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.ImageGrid
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.view.componentes.Pager
import com.todasbrillamos.viewmodel.MainVM

/**
 * Pantalla de inicio de la aplicación.
 * El usuario ve esta pantalla al abrir la aplicación.
 */
@Composable
fun HomeScreen(navController: NavHostController, mainVM: MainVM, modifier: Modifier = Modifier) {
    // Colectamos los productos desde el ViewModel
    val products by mainVM.products.collectAsState()

    // Llamamos a fetchProducts cuando se inicie la pantalla
    LaunchedEffect(Unit) {
        mainVM.fetchProducts()
    }

    Log.d("HomeScreen", "Number of products: ${products.size}")

    // Definir gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    // El contenido de la pantalla
    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Elemento de la imagen del logo
            item {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo zazil",
                    modifier = Modifier.size(150.dp)
                )
            }

            // Elemento del Pager
            item {
                Pager(
                    images = listOf(
                        R.drawable.imgprueba,
                        R.drawable.foto_acerca_de
                    )
                )
            }

            // Elemento del Grid con los productos
            item {
                // Pasamos directamente ProductInfo a ImageGrid
                ImageGrid(
                    products = products,
                    columns = 2,
                    onItemClick = { productId ->
                        // Navegar a la pantalla del producto con su ID
                        navController.navigate("item/$productId")
                    }
                )
            }
        }
    }
}


@Composable
@Preview
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController, MainVM())
}
