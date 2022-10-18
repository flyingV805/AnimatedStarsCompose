package kz.flyingv.animatedstarscompose.meteor

import androidx.compose.ui.graphics.Color
import kz.flyingv.animatedstarscompose.stars.StarConstraints
import kotlin.random.Random

fun createMeteorite(constraints: StarConstraints, width: Float, height: Float): Meteorite {

    return Meteorite(
        size = constraints.getRandomSize(),
        startX = width,
        startY = Random.nextDouble(0.0, height.toDouble()).toFloat(),
        color = Color.White
    )

}