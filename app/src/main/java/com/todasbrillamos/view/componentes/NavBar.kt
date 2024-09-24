package com.todasbrillamos.view.componentes

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.todasbrillamos.R

@Composable
fun NavBar() {

    val navigationItems = listOf(
        R.drawable.homebar,    // Iconos de la barra
        R.drawable.cartbar,
        R.drawable.about,
        R.drawable.userbar
    )

    var selectedIndex by remember { mutableStateOf(0) }

    AnimatedNavigationBar(
        modifier = Modifier.height(68.dp)
            .padding(all = 12.dp),
        selectedIndex = selectedIndex,
        cornerRadius = shapeCornerRadius(cornerRadius = 28.dp),
        ballAnimation = Straight(tween(300)),
        indentAnimation = Height(tween(300)),
        barColor = colorResource(id = R.color.rosaTB),
        ballColor = colorResource(id = R.color.rosaTB)
    ) {
        // items de navegacion
        navigationItems.forEachIndexed { index, item ->
            IconButton(
                onClick = {
                    selectedIndex = index
                }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = item),
                    contentDescription = null
                )
            }
        }
    }
}

