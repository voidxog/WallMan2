package com.colorata.wallman.wallpaper.own.daynight

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

object DayNightWallpaperPainter {
    var zoom: Float = 0f
    var bitmap: Bitmap? = null
    var desiredHeight: Int? = null
    var desiredWidth: Int? = null
    fun draw(c: Canvas, service: DayNightWallpaperService) {
        c.save()
        c.scale(1f - zoom / 10, 1f - zoom / 10, c.width / 2f, c.height / 2f)
        if (bitmap != null && desiredHeight != null && desiredWidth != null) {
            c.drawBitmap(bitmap!!, null, Rect(0, 0, desiredWidth!!, desiredHeight!!), null)
        }
        c.restore()
    }
}