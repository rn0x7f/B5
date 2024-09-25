package com.todasbrillamos.view.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ImageGrid(images: List<Int> = listOf()) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp)
    ) {
        items(images.size) { index ->
            ImageCard(imageRes = images[index])
        }
    }
}

@Composable
fun ImageCard(imageRes: Int) {
    Card(modifier = Modifier.padding(8.dp).size(100.dp)) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}



