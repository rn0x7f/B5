package com.todasbrillamos.view.componentes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

/**
 * Componente que muestra un carrusel de imágenes.
 *
 * @param images Lista de recursos de imágenes pasados como parámetros.
 * @param modifier Modificador opcional para personalizar la apariencia del carrusel.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pager(
    images: List<Int>,  // Lista de imágenes pasada como parámetro
    modifier: Modifier = Modifier
) {
    // Crear un estado de pager para controlar las páginas
    val pagerState = com.google.accompanist.pager.rememberPagerState(
        pageCount = images.size
    )

    // Efecto que hace que el carrusel se desplace automáticamente
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(3000)  // Esperar 3 segundos
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)  // Mover a la siguiente página
        }
    }

    val scope = rememberCoroutineScope()  // Coroutines scope para manejar el desplazamiento

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.wrapContentSize()) {
            // Componente HorizontalPager para mostrar las imágenes
            HorizontalPager(
                state = pagerState,
                modifier.wrapContentSize()
            ) { currentPage ->
                Card(
                    modifier
                        .wrapContentSize()
                        .padding(26.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[currentPage]),
                        contentDescription = ""
                    )
                }
            }

            // Botón siguiente
            IconButton(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < images.size) {
                        scope.launch {
                            pagerState.scrollToPage(nextPage)  // Navegar a la siguiente página
                        }
                    }
                },
                modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)  // Color de fondo del botón
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "",
                    modifier.fillMaxSize(),
                    tint = Color.LightGray  // Color del icono
                )
            }

            // Botón anterior
            IconButton(
                onClick = {
                    val prevPage = pagerState.currentPage - 1
                    if (prevPage >= 0) {
                        scope.launch {
                            pagerState.scrollToPage(prevPage)  // Navegar a la página anterior
                        }
                    }
                },
                modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)  // Color de fondo del botón
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "",
                    modifier.fillMaxSize(),
                    tint = Color.LightGray  // Color del icono
                )
            }
        }

        // Indicadores de la página
        PageIndicator(
            pageCount = images.size,
            currentPage = pagerState.currentPage,
            modifier = modifier
        )
    }
}

/**
 * Componente que muestra indicadores de la página actual.
 *
 * @param pageCount Número total de páginas.
 * @param currentPage Página actualmente seleccionada.
 * @param modifier Modificador opcional para personalizar la apariencia de los indicadores.
 */
@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            Indicadores(isSelected = it == currentPage, modifier = modifier)
        }
    }
}

/**
 * Componente que representa un indicador de página individual.
 *
 * @param isSelected Indica si el indicador está seleccionado.
 * @param modifier Modificador opcional para personalizar la apariencia del indicador.
 */
@Composable
fun Indicadores(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier.padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )
}