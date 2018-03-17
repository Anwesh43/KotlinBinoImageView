package ui.anwesome.com.binoimageview

/**
 * Created by anweshmishra on 17/03/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class BinoImageView(ctx : Context, var bitmap : Bitmap) : View(ctx) {
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
    data class BinoImage(var bitmap : Bitmap, var time : Int = 0) {
        val state = State()
        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width
            val h = canvas.height
            if (time == 0) {
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true)
            }
            val cy = h.toFloat()/2
            val cr = Math.min(w, h).toFloat()/4
            val cx = cr
            for( i in 0 .. 1) {
                canvas.save()
                val path = Path()
                path.addCircle(cx + (2 * cr * state.scales[1]) * i, cy, cr * state.scales[0], Path.Direction.CW)
                canvas.clipPath(path)
                canvas.drawBitmap(bitmap, 0f, 0f, paint)
                canvas.restore()
            }
            time++
        }
        fun update(stopcb : (Float) -> Unit)  {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : BinoImageView) {
        val binoImage : BinoImage = BinoImage(view.bitmap)
        val animator : Animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            binoImage.draw(canvas, paint)
            animator.animate {
                binoImage.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            binoImage.startUpdating {
                animator.start()
            }
        }
    }
}
