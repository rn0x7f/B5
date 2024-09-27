package com.todasbrillamos.view.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

// Definimos una clase de datos para representar los productos
data class Product(
    val imageRes: Int,
    val name: String,
    val price: String
)

@Composable
fun ImageGrid(products: List<Product> = listOf(), columns: Int = 2) {
    // Dividimos la lista de productos en filas
    val rows = products.chunked(columns)

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        // Creamos una fila por cada conjunto de productos
        rows.forEach { rowProducts ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween // Espacio entre las columnas
            ) {
                rowProducts.forEach { product ->
                    // Mostramos cada tarjeta de producto
                    ProductsCard(
                        productName = product.name,
                        productPrice = product.price,
                        imageRes = product.imageRes,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                }

                // Si la fila no está completa, añadimos un `Spacer` para mantener el layout
                if (rowProducts.size < columns) {
                    repeat(columns - rowProducts.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsCard(
    modifier: Modifier = Modifier,
    productName: String,
    productPrice: String,
    imageRes: Int // Añadimos este parámetro para la imagen del producto
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Card(
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier
                .fillMaxSize()
                .clickable { Unit }
                .background(Color.Transparent),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(4.dp)
                        .aspectRatio(1f / 1f), // Relación de aspecto cuadrada para la imagen
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(id = imageRes), // Aquí añadimos la imagen del producto
                            contentDescription = productName,
                            modifier = Modifier.fillMaxSize() // Aseguramos que la imagen ocupe todo el espacio del box
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = productName,
                    overflow = TextOverflow.Clip,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = productPrice,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}