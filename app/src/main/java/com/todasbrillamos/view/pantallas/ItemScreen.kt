package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.todasbrillamos.model.RemoteConnecter
import com.todasbrillamos.model.data.ProductInfo
import com.todasbrillamos.view.componentes.NavBar
import kotlinx.coroutines.launch

/**
 * Vista general de un objeto comprable
 */
@Composable
fun ItemScreen(navController: NavHostController, productID: Int, modifier: Modifier = Modifier) {
    // Definir el gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    // Estado para almacenar la información del producto
    var productInfo by remember { mutableStateOf<ProductInfo?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Efecto para obtener los detalles del producto cuando `productID` cambia
    LaunchedEffect(productID) {
        coroutineScope.launch {
            val remoteConnecter = RemoteConnecter()
            val productDetails = remoteConnecter.getProductById(productID)
            productInfo = productDetails
        }
    }

    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(28.dp)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors = gradientColors))
            ) {
                if (productInfo != null) {
                    // Cargar y mostrar la imagen del producto usando Coil
                    AsyncImage(
                        model = productInfo!!.imagen, // URL de la imagen
                        contentDescription = productInfo!!.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp), // Puedes ajustar el tamaño
                        contentScale = ContentScale.Crop // Escalar la imagen para que ocupe el espacio disponible
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Muestra los detalles del producto
                    Text(text = "Producto: ${productInfo!!.nombre}")
                    Text(text = "Precio: ${productInfo!!.precio}")
                    Text(text = "Descripción: ${productInfo!!.descripcion}")
                } else {
                    // Mensaje de carga
                    Text(text = "Cargando detalles del producto...")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemScreen() {
    val navController = rememberNavController()
    ItemScreen(navController, productID = 5)
}
