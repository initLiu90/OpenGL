package com.lzp.day4;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class AirHockeyRender implements GLSurfaceView.Renderer {
    private static final int BYTES_PER_FLOAT = 4;
    private Context context;
    private FloatBuffer vertexData;

    public AirHockeyRender(Context context) {
        this.context = context;

        float[] tableVertices = {
                //triangel1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,
                //triangle2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,
                //line
                -0.5f, 0,
                0.5f, 0,
                //point1
                0f, -0.25f,
                //pointer2
                0f, 0.25f
        };
        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)//分配本地内存
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVertices);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //从raw中读取定义的顶点着色器和片段着色器
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);

        //

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
