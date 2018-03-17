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
}