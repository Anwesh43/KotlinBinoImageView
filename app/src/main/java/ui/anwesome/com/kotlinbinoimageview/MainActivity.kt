package ui.anwesome.com.kotlinbinoimageview

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.binoimageview.BinoImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BinoImageView.create(this, BitmapFactory.decodeResource(resources, R.drawable.nature_more))
    }
}
