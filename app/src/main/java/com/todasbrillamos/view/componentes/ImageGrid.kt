package com.todasbrillamos.view.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.todasbrillamos.model.data.ProductInfo

@Composable
fun ImageGrid(products: List<ProductInfo> = listOf(), columns: Int = 2) {
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
                        productName = product.nombre,
                        productPrice = product.precio.toString(),
                        imageUrl = product.imagen,  // Usamos la URL desde ProductInfo
                        onClick = {

                        },
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
    onClick: () -> Unit,
    imageUrl: String // Usamos la URL de la imagen desde ProductInfo
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Card(
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
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
                        // Cargar la imagen desde la URL usando Coil
                        AsyncImage(
                            model = imageUrl, // URL de la imagen
                            contentDescription = productName,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // Ajustamos la imagen al tamaño del contenedor
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    text = productName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = productPrice, // Precio como cadena de texto
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
