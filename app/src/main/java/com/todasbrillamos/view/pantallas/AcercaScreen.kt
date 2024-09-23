package com.todasbrillamos.view.pantallas


import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.todasbrillamos.R
import com.todasbrillamos.view.componentes.Espaciador
import com.todasbrillamos.view.componentes.Pager
import com.todasbrillamos.view.componentes.TextoNormal
import com.todasbrillamos.view.componentes.TextoResaltado
import com.todasbrillamos.view.componentes.TextoResaltadoMini

@Preview
@Composable
fun AcercaScreen() {

    val navigationItems = listOf(
        R.drawable.home,    // Replace with your actual drawable resources
        R.drawable.phone,
        R.drawable.mail
    )
    var selectedIndex by remember { mutableStateOf(0) }

    // Define gradient
    val gradientColors = listOf(
        Color(0xFFffe5b4), // Initial color
        Color(0xFFffbba8)  // Final color
    )

    val uriHandler = LocalUriHandler.current

    Scaffold(
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier.height(49.dp),
                selectedIndex = selectedIndex,
                cornerRadius = shapeCornerRadius(cornerRadius = 43.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = colorResource(id = R.color.rosaTB),
                ballColor = colorResource(id = R.color.rosaTB)
            ) {
                // Create navigation bar items without onItemSelected
                navigationItems.forEachIndexed { index, item ->
                    IconButton(
                        onClick = {
                            selectedIndex = index
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = item),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = gradientColors))
                .padding(innerPadding)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = Brush.linearGradient(colors = gradientColors))
            ) {
                TextoResaltado(value = "Acerca de nosotras: ")
                Pager(
                    images = listOf(
                        R.drawable.imgprueba,
                        R.drawable.foto_acerca_de
                    )
                )
                Espaciador()
            }
        }
    }
}
