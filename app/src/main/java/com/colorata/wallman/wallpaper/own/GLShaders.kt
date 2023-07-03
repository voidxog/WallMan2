package com.colorata.wallman.wallpaper.own

import android.opengl.GLES20.GL_COMPILE_STATUS
import android.opengl.GLES20.GL_LINK_STATUS
import android.opengl.GLES20.glAttachShader
import android.opengl.GLES20.glCompileShader
import android.opengl.GLES20.glCreateProgram
import android.opengl.GLES20.glCreateShader
import android.opengl.GLES20.glDeleteProgram
import android.opengl.GLES20.glDeleteShader
import android.opengl.GLES20.glGetProgramiv
import android.opengl.GLES20.glGetShaderiv
import android.opengl.GLES20.glLinkProgram
import android.opengl.GLES20.glShaderSource


object GLShaders {

    const val fragmentShader = """
        precision mediump float;
        uniform sampler2D u_TextureUnit;
        varying vec2 v_Texture;
        void main() {
            gl_FragColor = texture2D(u_TextureUnit, v_Texture);
        }
    """

    const val vertexShader = """
        attribute vec4 a_Position;
        uniform mat4 u_Matrix;
        attribute vec2 a_Texture;
        varying vec2 v_Texture;
        void main() {
            gl_Position = u_Matrix * a_Position;
            v_Texture = a_Texture;
        }
    """

    fun createProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = glCreateProgram()
        if (programId == 0) {
            return 0
        }

        glAttachShader(programId, vertexShaderId)
        glAttachShader(programId, fragmentShaderId)

        glLinkProgram(programId)
        val linkStatus = IntArray(1)
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            glDeleteProgram(programId)
            return 0
        }
        return programId
    }

    fun createShader(type: Int, text: String): Int {
        val shaderId = glCreateShader(type)
        if (shaderId == 0) {
            return 0
        }
        glShaderSource(shaderId, text)
        glCompileShader(shaderId)
        val compileStatus = IntArray(1)
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderId)
            return 0
        }
        return shaderId
    }
}