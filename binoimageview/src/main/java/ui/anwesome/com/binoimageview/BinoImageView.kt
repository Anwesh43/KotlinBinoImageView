package ui.anwesome.com.binoimageview

/**
 * Created by anweshmishra on 17/03/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class BinoImageView(ctx : Context) : View(ctx) {
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0, var jDir : Int = 1) {
        val scales : Array<Float> = arrayOf(0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if(Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += jDir
                if (j == scales.size || j == -1) {
                    jDir *= -1
                    j += jDir
                    scales[j] = prevScale
                    dir = 0f
                    stopcb(scales[j])
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            try {
                Thread.sleep(50)
                view.invalidate()
            }
            catch(ex : Exception) {

            }
        }
        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
}
