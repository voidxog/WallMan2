package com.colorata.wallman.wallpaper.own.textwallpaper

import android.content.res.AssetManager
import android.graphics.*
import android.hardware.display.DisplayManager
import android.text.TextPaint
import android.view.Surface
import androidx.annotation.IntRange

class TextWallpaperPainter {
    private var _typeface: Typeface.Builder? = null
    var zoom: Float = 0f
    fun draw(c: Canvas, service: TextWallpaperService) {
        c.save()
        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        c.scale(1f - zoom / 10, 1f - zoom / 10, c.width / 2f, c.height / 2f)
        c.rotate(
            when (service.getSystemService(DisplayManager::class.java).displays[0].rotation) {
                Surface.ROTATION_90 -> {
                    -90f
                }
                Surface.ROTATION_270 -> {
                    90f
                }
                else -> 0f
            }
        )
        for (i in 1 until 8) {
            val textPaint = TextPaint().apply {
                typeface = variableTypeface(service.assets, i * 100, 120)
                color = Color.WHITE
                setTextSizeForWidth(c.width.toFloat(), "07:30")
            }
            c.drawText("07:30", 0f, c.height * (i.toFloat()) / 7, textPaint)
        }
        c.restore()
    }

    fun variableTypeface(
        assets: AssetManager,
        @IntRange(from = 100L, to = 999L) weight: Int,
        @IntRange(from = 25L, to = 151L) width: Int
    ): Typeface {
        if (_typeface == null) {
            _typeface = Typeface.Builder(
                assets, "font/robot_flex.ttf"
            )
        }
        return _typeface?.setFontVariationSettings("'wght' $weight, 'wdth' $width")?.build()
            ?: Typeface.DEFAULT
    }

    private fun Paint.setTextSizeForWidth(
        desiredWidth: Float, text: String
    ) {
        val firstSize = 120f

        textSize = firstSize
        val bounds = Rect()
        getTextBounds(text, 0, text.length, bounds)

        val desiredTextSize: Float = firstSize * desiredWidth / bounds.width()

        // Set the paint for that size.
        textSize = desiredTextSize
    }

    class TextVariables(
        private val painter: TextWallpaperPainter,
        private val service: TextWallpaperService
    ) {
        var weight: Float = 400f
            set(value) {
                field = value
                globalCanvas?.let { painter.draw(it, service) }
            }
        var width: Float = 100f
            set(value) {
                field = value
                globalCanvas?.let { painter.draw(it, service) }
            }
        var scale: Float = 1f
            set(value) {
                field = value
                globalCanvas?.let { painter.draw(it, service) }
            }
        var fade: Float = 1f
            set(value) {
                field = value
                globalCanvas?.let { painter.draw(it, service) }
            }
    }
}