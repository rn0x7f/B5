package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.todasbrillamos.model.data.CartItem
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.viewmodel.MainVM
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun CarritoScreen(navController: NavHostController, mainVM: MainVM) {
    val estado = mainVM.userCart.collectAsState()

    // Remember the error state for displaying the out-of-stock message
    var outOfStockMessage by remember { mutableStateOf("") }

    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    Scaffold(bottomBar = { NavBar(navController) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(innerPadding)
                .padding(28.dp)
        ) {
            TextoResaltado(value = "Carrito de compras")
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(estado.value) { cartItem: CartItem ->
                    ProductItem(cartItem, mainVM) { message ->
                        outOfStockMessage = message // Update out-of-stock message
                    }
                    HorizontalDivider()
                }
            }

            if (estado.value.isEmpty()) {
                TextoNormal(value = "No hay productos en el carrito", 20)
            }

            // Display the out-of-stock message
            if (outOfStockMessage.isNotEmpty()) {
                Text(
                    text = outOfStockMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if (estado.value.isNotEmpty()) {
                // Total price calculation can be added here, for example:

                TextoNormal(value = "Total: ${String.format(Locale.getDefault(),"%.2f", mainVM.calculateTotal())}")
                var description: String = ""
                estado.value.forEach {
                    description += it.product.nombre + " - " + it.quantity + "\n"
                }
                Button(onClick = {navController.navigate("pago/${mainVM.calculateTotal()}/$description")}){
                    Text(text = "Proceder al pago")
                }
            }
        }
    }
}

@Composable
fun ProductItem(cartItem: CartItem, mainVM: MainVM, onOutOfStock: (String) -> Unit) {

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Imagen del producto
        AsyncImage(
            model = cartItem.product.imagen,
            contentDescription = cartItem.product.nombre,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nombre del producto
        Text(text = cartItem.product.nombre, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(8.dp))

        // Precio y cantidad
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f) // Allow price text to take available space
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "Decrease quantity",
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            mainVM.removeFromCartBack(cartItem)
                            mainVM.removeFromCart(cartItem.product)
                            mainVM.calculateTotal()

                        }
                    }
                )

                Text(text = "Cantidad: ${cartItem.quantity}", fontSize = 16.sp)

                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Increase quantity",
                    modifier = Modifier.clickable {
                        coroutineScope.launch {
                            val success = mainVM.addToCartBack(cartItem)
                            if (success) {
                                mainVM.addToCart(cartItem.product)
                                mainVM.calculateTotal()
                            } else {
                                onOutOfStock("No hay más existencias de: ${cartItem.product.nombre}")
                            }
                        }
                    }
                )

                Text(text = "$${cartItem.product.precio * cartItem.quantity}", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun PreviewCarritoScreen() {
    val navController = rememberNavController()
    CarritoScreen(navController, MainVM())
}