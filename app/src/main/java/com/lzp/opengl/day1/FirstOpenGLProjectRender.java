package com.lzp.opengl.day1;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class FirstOpenGLProjectRender implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置清空屏幕用的颜色，当屏幕被清空时，就会填充红色
        //前三个参数分别对应红色、绿色、蓝色，最后一个参数为alpha，用来表示般透明度或透明度
        //颜色范围在0-1之间：1最大，0最小
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视口(viewport)尺寸，告诉OpenGL可以用来渲染的surface的大小
        //set the OpenGL viewport to fill the entire surface
        glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //clear the rendering surface
        //清空屏幕，这会擦出屏幕上的所有颜色，并用之前glClearColor()调用定义的颜色填充整个屏幕
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
