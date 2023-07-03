package com.colorata.wallman.wallpaper.own.daynight

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import com.colorata.wallman.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL
import javax.microedition.khronos.opengles.GL10

class Triangle(private val context: Context) {

    val textureIds = IntArray(1)

    val vertexBuffer by lazy {
        val vertexByteBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
        vertexByteBuffer.order(ByteOrder.nativeOrder())
        val vertexBuffer = vertexByteBuffer.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)
        vertexBuffer
    }
    private val vertices = floatArrayOf(
        -1f, -0.5f, 0.0f,  // V1 - first vertex (x,y,z)
        0.5f, -0.5f, 0.0f,  // V2 - second vertex
        0.0f, 0.5f, 0.0f // V3 - third vertex
    )

    val bitmap by lazy {
        BitmapFactory.decodeResource(context.resources, R.drawable.p6a_floral_v1_light_preview)
    }

    fun draw(gl: GL10) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glGenTextures(1, textureIds, 0)
        gl.glActiveTexture(GL10.GL_TEXTURE0)
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIds[0])
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)
        gl.glEnable(GL10.GL_BLEND)
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR)
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR)
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0)
        //bitmap.recycle()
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0)
        /*
        gl.glColor4f(0f, 1f, 0f, 1f)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.size / 3)*/
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
    }
}