package kz.flyingv.animatedstarscompose

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kz.flyingv.animatedstarscompose.meteor.Meteorite
import kz.flyingv.animatedstarscompose.meteor.createMeteorite
import kz.flyingv.animatedstarscompose.stars.Star
import kz.flyingv.animatedstarscompose.stars.StarConstraints
import kz.flyingv.animatedstarscompose.stars.StarListener
import kz.flyingv.animatedstarscompose.stars.createStar
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask
import kotlin.random.Random
import kotlin.time.ExperimentalTime

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalTime::class)
@Composable
fun StarsView(
    modifier: Modifier,
    fps: Int = 60,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {

    val scope = rememberCoroutineScope()
    val composition = currentComposer.composition
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }
    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }
    var invalidations by remember{ mutableStateOf(0) }

    val starConstraints = remember { StarConstraints(0.5.dp, 3.0.dp, density) }
    val stars = remember { arrayListOf<Star>() }
    val meteor = remember { mutableStateOf<Meteorite?>(null) }

    Box(modifier = modifier){

        Canvas(
            modifier = Modifier.fillMaxSize(),
        ){
            invalidations.let {

                val newArray = ArrayList(stars)
                newArray.forEach { star ->
                    star.draw(this)
                }

                meteor.value?.draw(this)

            }
        }

    }

    DisposableEffect(lifecycleOwner) {

        var timer: Timer? = null
        var task: TimerTask?
        val toRemove = arrayListOf<Int>()

        val observer = LifecycleEventObserver { _, event ->

            when(event){
                Lifecycle.Event.ON_CREATE -> {
                    for(i in 0 until 75){
                        stars.add(
                            createStar(i, starConstraints, screenWidth, screenHeight)
                        )
                    }

                    meteor.value = createMeteorite(starConstraints, screenWidth, screenHeight)

                }
                Lifecycle.Event.ON_RESUME -> {
                    timer = Timer()
                    task = timerTask {
                        stars.forEachIndexed { index, star ->
                            val isComplete = star.calc()
                            if(isComplete){ toRemove.add(index) }
                        }
                        toRemove.forEach {
                            stars.removeAt(it)
                            stars.add(it, createStar(it, starConstraints, screenWidth, screenHeight))
                        }
                        toRemove.clear()

                        meteor.value?.calc(screenWidth)

                        if(meteor.value?.finished == true){
                            meteor.value = createMeteorite(starConstraints, screenWidth, screenHeight)
                        }

                        invalidations++

                    }

                    timer?.scheduleAtFixedRate(task, 0, (1000/fps).toLong())
                }
                Lifecycle.Event.ON_PAUSE -> {
                    timer?.cancel()
                }
                else -> {}
            }

        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

}

