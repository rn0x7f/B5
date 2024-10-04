package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado

@Composable
fun CarritoScreen(navController: NavHostController, mainVM: MainVM) {
    val estado = mainVM.userCart.collectAsState()

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
                    ProductItem(cartItem, mainVM)
                    HorizontalDivider()
                }
            }

            if (estado.value.isEmpty()) {
                TextoNormal(value = "No hay productos en el carrito", 20)
            }

            // Calculate total from ViewModel
            val total = mainVM.calculateTotal()
            if (total > 0) {
                TextoNormal(value = "Total: $${total}", 20)
            }
        }
    }
}

@Composable
fun ProductItem(cartItem: CartItem, mainVM: MainVM) {
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
                        mainVM.removeFromCart(cartItem.product)
                        mainVM.calculateTotal()
                    }
                )

                Text(text = "Cantidad: ${cartItem.quantity}", fontSize = 16.sp)

                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Increase quantity",
                    modifier = Modifier.clickable {
                        mainVM.addToCart(cartItem.product)
                        mainVM.calculateTotal()
                    }
                )

                Text(text = "$${cartItem.product.precio * cartItem.quantity}", fontSize = 16.sp)
            }


        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}