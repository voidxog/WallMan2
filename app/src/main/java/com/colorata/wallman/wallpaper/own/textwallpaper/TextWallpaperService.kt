package com.colorata.wallman.wallpaper.own.textwallpaper

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder

var globalCanvas: Canvas? = null
class TextWallpaperService : WallpaperService() {
    override fun onCreateEngine() = TextWallpaperEngine()
    val engineStorage: MutableSet<TextWallpaperEngine> = mutableSetOf()

    inner class TextWallpaperEngine : Engine() {
        private val painter = TextWallpaperPainter()
        private val redrawHandler = RedrawHandler()

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            engineStorage.add(this)
        }

        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            redrawHandler.planRedraw()
        }


        override fun onSurfaceChanged(
            holder: SurfaceHolder?,
            format: Int,
            width: Int,
            height: Int
        ) {
            super.onSurfaceChanged(holder, format, width, height)
            redrawHandler.planRedraw()
        }

        override fun onSurfaceRedrawNeeded(holder: SurfaceHolder) {
            super.onSurfaceRedrawNeeded(holder)
            redrawHandler.omitRedraw()
            drawSynchronously(holder)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            redrawHandler.planRedraw()
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            redrawHandler.omitRedraw()
        }

        override fun onZoomChanged(zoom: Float) {
            super.onZoomChanged(zoom)
            painter.zoom = zoom
            redrawHandler.planRedraw()
        }

        override fun onDestroy() {
            super.onDestroy()
            redrawHandler.omitRedraw()
            engineStorage.remove(this)
        }

        fun drawSynchronously() = drawSynchronously(surfaceHolder)

        private fun drawSynchronously(holder: SurfaceHolder) {
            if (!isVisible) return

            try {
                globalCanvas = holder.lockCanvas()
                globalCanvas?.let {
                    painter.draw(it, this@TextWallpaperService)
                }
            } finally {
                globalCanvas?.let {
                    holder.unlockCanvasAndPost(it)
                }
            }
        }

        @SuppressLint("HandlerLeak")
        inner class RedrawHandler : Handler(Looper.getMainLooper()) {
            private val redraw = 1

            fun omitRedraw() {
                removeMessages(redraw)
            }

            fun planRedraw() {
                omitRedraw()
                sendEmptyMessage(redraw)
            }

            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    redraw -> drawSynchronously()
                    else -> super.handleMessage(msg)
                }
            }
        }
    }
}