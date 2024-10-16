package com.todasbrillamos.view.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.todasbrillamos.model.data.CartItem
import com.todasbrillamos.view.componentes.NavBar
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.viewmodel.MainVM

@Composable
fun PedidosScreen(navController: NavHostController, mainVM: MainVM) {

    // Definir gradiente
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Color inicial
        Color(0xFFffbba8)  // Color final
    )

    // Collecting cart history from the ViewModel as state
    val cartHistory = mainVM.userCartHistory.collectAsState()

    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(innerPadding)
                .padding(28.dp)
        ) {
            TextoResaltado(value = "Mis pedidos")

            // Displaying the history in a vertical list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Add space between items
            ) {
                itemsIndexed(cartHistory.value) { index, cartItems ->
                    CartHistoryCard(cartItems, index)
                }
            }
        }
    }
}

@Composable
fun CartHistoryCard(cartItems: List<CartItem>, index: Int) {
    val totalItems = cartItems.sumOf { it.quantity }
    val totalPrice = cartItems.sumOf { it.product.precio.toDouble() * it.quantity }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Pedido #${index + 1}")
            Text(text = "Total de artículos: $totalItems")
            Text(text = "Precio total: $${String.format("%.2f", totalPrice)}")
        }
    }
}

@Preview
@Composable
fun PreviewPedidosScreen() {
    val navController = rememberNavController()
    PedidosScreen(navController, mainVM = MainVM())
}