package com.lzp.opengl;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertextData;

    public AirHockeyRenderer() {
        //由于在OpenGL中只能绘制点、直线、三角形，
        //所有这里用两个直角三角形拼接在一起组成长方形
        float[] tableVertices = {
                //Triangle1
                0f, 0f,
                9f, 14f,
                0f, 14f,
                //Triangle2
                0f, 0f,
                9f, 0f,
                9f, 14f,
                //Line1
                0f, 7f,
                9f, 7f,
                //Mallets
                4.5f, 2f,
                4.5f, 12f
        };

        vertextData = ByteBuffer.allocate(tableVertices.length * BYTES_PER_FLOAT)//利用ByteBuffer分配一块本地内存，这块内存不会被垃圾回收器管理。
                .order(ByteOrder.nativeOrder())//告诉字节缓冲区按照本地字节序组织它的内容
                .asFloatBuffer();//得到一个可以反映底层字节的FLoatBuffer类实例
        vertextData.put(tableVertices);//把数据从Dalvik的内存复制到本地内存
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {

    }

    @Override
    public void onDrawFrame(GL10 gl10) {

    }
}
