package kz.flyingv.animatedstarscompose.stars

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun createStar(colors: List<Color>, constraints: StarConstraints, width: Float, height: Float): Star{
    return Star(
        x = Random.nextDouble(0.0, width.toDouble()).toFloat(),
        y = Random.nextDouble(0.0, height.toDouble()).toFloat(),
        constraints =  constraints,
        color = colors.random()
    )
}