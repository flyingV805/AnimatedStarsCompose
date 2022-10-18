package kz.flyingv.animatedstarscompose.stars

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun createStar(id: Int, constraints: StarConstraints, width: Float, height: Float): Star{
    return Star(
        id = id,
        x = Random.nextDouble(0.0, width.toDouble()).toFloat(),
        y = Random.nextDouble(0.0, height.toDouble()).toFloat(),
        constraints =  constraints,
        color = Color.White
    )
}