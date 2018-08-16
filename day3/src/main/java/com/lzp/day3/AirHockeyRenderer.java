package com.lzp.day3;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertextData;

    private Context mContext;

    private int program;
//    private static final String U_COLOR = "u_Color";
//    private int uColorLocation;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;

    private static final String A_COLOR = "a_Color";
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private int aColorLocation;

    public AirHockeyRenderer(Context context) {
        //由于在OpenGL中只能绘制点、直线、三角形，
        //所有这里用两个直角三角形拼接在一起组成长方形
        float[] tableVertices = {
                //Triangle fan
                0, 0, 1f, 1f, 1f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                //Line1
                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,
                //Mallets
                0f, -0.25f, 0f, 0f, 1f,
                0f, 0.25f, 1f, 0f, 0f
        };
        vertextData = ByteBuffer
                .allocateDirect(tableVertices.length * BYTES_PER_FLOAT)//利用ByteBuffer分配一块本地内存，这块内存不会被垃圾回收器管理。
                .order(ByteOrder.nativeOrder())//告诉字节缓冲区按照本地字节序组织它的内容
                .asFloatBuffer();//得到一个可以反映底层字节的FLoatBuffer类实例
        vertextData.put(tableVertices);//把数据从Dalvik的内存复制到本地内存
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);//黑色
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        ShaderHelper.validateProgram(program);
        glUseProgram(program);//告诉OpenGL在绘制任何东西到屏幕上的时候要使用这里定义的程序

//        uColorLocation = glGetUniformLocation(program, U_COLOR);//获取uniform的位置
        aColorLocation = glGetAttribLocation(program, A_COLOR);//获取a_Color的位置
        aPositionLocation = glGetAttribLocation(program, A_POSITION);//获取属性的位置，有了这个位置就能告诉OpenGL到哪里去找到这个属性对应的数据了

        //关联属性与顶点数据的数组
        //告诉OpenGL，它可以在缓冲区vertexData中找到a_Position对应的数据
        vertextData.position(0);
//        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertextData);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertextData);
        //使能顶点数组
        glEnableVertexAttribArray(aPositionLocation);

        vertextData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertextData);
        glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        //在屏幕上绘制
        //绘制桌子
//        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);//更新着色器代码中的u_Color的值。
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);//绘制三角形扇
        //绘制分割线
//        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);//红色
        glDrawArrays(GL_LINES, 6, 2);
        //绘制点
//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);//蓝色
        glDrawArrays(GL_POINTS, 8, 1);

//        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);//红色
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
