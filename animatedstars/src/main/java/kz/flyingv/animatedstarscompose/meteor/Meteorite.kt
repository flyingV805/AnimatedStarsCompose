package kz.flyingv.animatedstarscompose.meteor

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.random.Random

class Meteorite(
    startX: Float,
    startY: Float,
    private val size: Float,
    val color: Color
) {

    private val factor = (size * Random.nextDouble(0.01, 2.0) * 1.9).toFloat()
    private val trailLength = Random.nextInt(20, 180)

    var x: Float = startX
    var y: Float = startY

    var finished = false

    fun calc(viewWidth: Float) {

        if (x < (viewWidth * -0.5)) {
            finished = true
        }

        if(finished){
            return
        }

        x -= factor
        y += factor

    }


    fun draw(scope: DrawScope){

        //Log.d("METEOR DRAW", "DRAW AT $x $y")

        scope.drawCircle(
            color = color,
            radius = size,
            center = Offset(x = x, y = y),
            style = Fill
        )

        scope.drawLine(
            brush = Brush.linearGradient(
                colors = listOf(color, Color.Transparent),
                start = Offset(x, y),
                end = Offset(x + trailLength, y - trailLength)
            ),
            start = Offset(x, y),
            end = Offset(x + trailLength, y - trailLength),
            strokeWidth = size * 2
        )

    }

}