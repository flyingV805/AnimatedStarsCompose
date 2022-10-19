package kz.flyingv.animatedstarscompose.stars

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.random.Random

class Star(
    private val x: Float,
    private val y: Float,
    constraints: StarConstraints,
    private val color: Color
) {

    private val size = constraints.getRandomSize()

    private var alpha = 0.0f
    private var multiplierFactor = Random.nextDouble(0.01, 0.09).toFloat()
    private var incrementFactor = Random.nextDouble(0.1, 0.3).toFloat()

    fun calc(): Boolean {

        if (alpha < 0.0) {
            return true
        }

        if(alpha > 1.0f){
            multiplierFactor *= -1.0f
        }

        alpha += incrementFactor * multiplierFactor

        return false
    }

    fun draw(scope: DrawScope){
        scope.drawCircle(
            color = color.copy(alpha = alpha.coerceIn(0f, 1f)),
            radius = size,
            center = Offset(x = x, y = y),
            style = Fill
        )
    }


}