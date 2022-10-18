package kz.flyingv.animatedstarscompose.stars

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlin.random.Random

class StarConstraints(
    private val minStarSize: Dp,
    private val maxStarSize: Dp,
    private val localDensity: Density
) {

    fun getRandomSize(): Float {
        return Random.nextDouble(
            with(localDensity){minStarSize.toPx()}.toDouble(),
            with(localDensity){maxStarSize.toPx()}.toDouble()
        ).toFloat()
    }


}