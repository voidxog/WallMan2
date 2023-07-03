package com.colorata.wallman.wallpaper.own.daynight

import android.app.WallpaperColors
import android.opengl.GLSurfaceView
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

/*gl.glClearColor(1f, 1f, 0.0f, 1.0f)
               val texture = intArrayOf(1)
               gl.glGenTextures(1, texture, 0)
               gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0])
               val texCoords = floatArrayOf(0.0f, 0.0f, 1.0f, 0.0f)
               val texcoords = FloatBuffer.wrap(texCoords)
               gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texcoords)
               val bitmap =
                   BitmapFactory.decodeResource(resources, R.drawable.p6a_floral_v1_light_preview)
               gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR)
               gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR)

               GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0)


               gl.glEnable(GL10.GL_TEXTURE_2D)
               gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)*/

class DayNightWallpaperService : WallpaperService() {
    override fun onCreateEngine() = DayNightWallpaperEngine()

    inner class DayNightWallpaperEngine : Engine() {

        private val shaderRenderer by lazy {
            DayNightRenderer(applicationContext)
        }
        private val lock = Any()
        private val surfaceView by lazy {
            object : GLSurfaceView(this@DayNightWallpaperService) {
                override fun getHolder(): SurfaceHolder {
                    return this@DayNightWallpaperEngine.surfaceHolder
                }
            }
        }

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            surfaceView.setEGLContextClientVersion(2)
            surfaceView.setRenderer(shaderRenderer)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            synchronized(lock) {
                if (visible) {
                    surfaceView.onResume()
                } else {
                    surfaceView.onPause()
                }
            }
        }

        override fun onSurfaceChanged(
            holder: SurfaceHolder,
            format: Int,
            width: Int,
            height: Int
        ) {
            synchronized(lock) {
                surfaceView.surfaceChanged(holder, format, width, height)
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            synchronized(lock) {
                surfaceView.surfaceCreated(holder)
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            synchronized(lock) {
                surfaceView.surfaceDestroyed(holder)
            }
        }

    }
}