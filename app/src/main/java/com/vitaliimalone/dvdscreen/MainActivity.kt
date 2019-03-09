package com.vitaliimalone.dvdscreen

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawView = DrawView()
        setContentView(drawView)
        drawView.keepScreenOn = true
    }

    inner class DrawView : View(this) {
        private var p = Paint().also { it.setRandomBrightColor() }
        private var dvdX = 0f
        private var dvdY = 0f
        private var xSpeed = 10
        private var ySpeed = 10
        private var options = BitmapFactory.Options().also { it.inSampleSize = 2 }
        private val dvdLogo = BitmapFactory.decodeResource(resources, R.drawable.dvd_logo, options)

        init {
            setBackgroundColor(Color.BLACK)
        }

        override fun onDraw(canvas: Canvas) {
            dvdX += xSpeed
            dvdY += ySpeed
            checkBounce()
            canvas.drawBitmap(dvdLogo, dvdX, dvdY, p)
            invalidate()
        }

        private fun checkBounce() {
            if (dvdX + dvdLogo.width >= width) {
                xSpeed = -xSpeed
                dvdX = (width - dvdLogo.width).toFloat()
                p.setRandomBrightColor()
            } else if (dvdX <= 0) {
                xSpeed = -xSpeed
                dvdX = 0f
                p.setRandomBrightColor()
            }
            if (dvdY + dvdLogo.height >= height) {
                ySpeed = -ySpeed
                dvdY = (height - dvdLogo.height).toFloat()
                p.setRandomBrightColor()
            } else if (dvdY <= 0) {
                ySpeed = -ySpeed
                dvdY = 0f
                p.setRandomBrightColor()
            }
        }

        private fun Paint.setRandomBrightColor() {
            val randomColor = Color.argb(255, (100..255).random(), (100..255).random(), (100..255).random())
            colorFilter = PorterDuffColorFilter(randomColor, PorterDuff.Mode.SRC_IN)
        }
    }
}
