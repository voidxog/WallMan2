package com.colorata.wallman.wallpaper.own

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20.GL_BLEND
import android.opengl.GLES20.GL_LINEAR
import android.opengl.GLES20.GL_ONE_MINUS_CONSTANT_ALPHA
import android.opengl.GLES20.GL_SRC_ALPHA
import android.opengl.GLES20.GL_TEXTURE0
import android.opengl.GLES20.GL_TEXTURE_2D
import android.opengl.GLES20.GL_TEXTURE_MAG_FILTER
import android.opengl.GLES20.GL_TEXTURE_MIN_FILTER
import android.opengl.GLES20.glActiveTexture
import android.opengl.GLES20.glBindTexture
import android.opengl.GLES20.glBlendFunc
import android.opengl.GLES20.glDeleteTextures
import android.opengl.GLES20.glEnable
import android.opengl.GLES20.glGenTextures
import android.opengl.GLES20.glTexParameteri
import android.opengl.GLUtils
import androidx.annotation.DrawableRes

object GLTextures {

    fun loadTexture(context: Context, @DrawableRes id: Int): Int {
        val textureIds = IntArray(1)
        glGenTextures(1, textureIds, 0)
        if (textureIds[0] == 0) return 0
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inScaled = false
        val bitmap = BitmapFactory.decodeResource(context.resources, id, bitmapOptions)
        if (bitmap == null) {
            glDeleteTextures(1, textureIds, 0)
            return 0
        }
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureIds[0])
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA)
        glEnable(GL_BLEND)

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()

        glBindTexture(GL_TEXTURE_2D, 0)
        return textureIds[0]
    }
}