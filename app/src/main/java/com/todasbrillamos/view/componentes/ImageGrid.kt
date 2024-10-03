package com.todasbrillamos.view.componentes

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
fun ImageGrid(
    products: List<ProductInfo>,  // Asegúrate de no tener una lista vacía por defecto si siempre recibirás datos
    columns: Int = 2,
    onItemClick: (Int) -> Unit // Función que acepta el ID del producto
) {
    val rows = products.chunked(columns)

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        rows.forEach { rowProducts ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowProducts.forEach { product ->
                    ProductsCard(
                        productName = product.nombre,
                        productPrice = product.precio.toString(),
                        imageUrl = product.imagen,
                        onClick = { onItemClick(product.id_producto) },  // Llamamos a onItemClick con el ID del producto
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )
                }

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
    onClick: () -> Unit,  // Este onClick ahora es pasado desde ImageGrid
    imageUrl: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Card(
            elevation = CardDefaults.cardElevation(0.dp),
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }  // Ejecuta onClick cuando se hace clic
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
                        .aspectRatio(1f / 1f),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = productName,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
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
                    text = productPrice,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
