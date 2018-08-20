package com.lzp.day4;

import android.content.Context;
import android.graphics.Shader;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

public class AirHockeyRender implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 2;//顶点位置属性包含的分量（x，y）
    private static final int COLOR_COMPONENT_COUNT = 3;//顶点颜色属性包含的分量（r、g、b）
    private static final int BYTES_PER_FLOAT = 4;
    //因为在同一个数据数组里面既有位置又有颜色属性，opengl不能再假定下一个位置是紧跟着前一个位置的。
    // 一旦opengl读入了一个顶点的位置，如果他想读入下一个顶点位置，他不得不跳过当前顶点的颜色数据。
    //我们要用那么个跨距（stride）告诉OpenGl每个位置之间有多少个字节。这样他就知道需要跳过多少了。
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    //    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private static final String A_COLOR = "a_Color";

    private Context context;
    private FloatBuffer vertexData;
    private int program;

    private int uColorLocation, aPositionLocation, aColorLocation;

    public AirHockeyRender(Context context) {
        this.context = context;

//        float[] tableVertices = {
//                //triangel1
//                -0.5f, -0.5f,
//                0.5f, 0.5f,
//                -0.5f, 0.5f,
//                //triangle2
//                -0.5f, -0.5f,
//                0.5f, -0.5f,
//                0.5f, 0.5f,
//                //line
//                -0.5f, 0,
//                0.5f, 0,
//                //point1
//                0f, -0.25f,
//                //pointer2
//                0f, 0.25f
//        };
        //定义三角扇形，以及为每个顶点增加颜色属性
        float[] tableVertices = {
                //Triangle Fan
                0, 0, 1f, 1f, 1f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.5f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                //line
                -0.5f, 0f, 1f, 0f, 1f,
                0.5f, 0f, 1f, 0f, 0f,
                //point1
                0f, -0.25f, 0f, 0f, 1f,
                //pointer2
                0f, 0.25f, 1f, 0f, 0f
        };

        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)//分配本地内存
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexData.put(tableVertices);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        //1、读取定义的着色器代码
        //2、创建着色器对象并编译着色器代码
        //3、创建OpenGL程序对象，关联顶点着色器和片段着色器
        //4、验证OpenGL程序对象
        //5、使用现创建的OpenGL程序对象
        //6、获取顶点着色中a_Position位置，片段着色器中u_Color的位置
        //7、关联属性与顶点数据数组
        //8、使能顶点数组


        //从raw中读取定义的顶点着色器和片段着色器
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);

        //获取着色器id
        int vertexShaderId = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShaderId = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        //顶点着色器和片段着色器关联
        program = ShaderHelper.linkProgram(vertexShaderId, fragmentShaderId);
        //验证OpenGL程序对象
        ShaderHelper.validateProgram(program);
        //告诉OpenGL在绘制任何东西到屏幕上的时候要使用这里定义的程序
        glUseProgram(program);

        //获取属性的位置，有了这个位置就能告诉OpenGL到哪里去找到这个属性对应的数据了
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
//        //获取片段着色器中uniform的位置
//        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aColorLocation = glGetAttribLocation(program, A_COLOR);

        //关联属性与定点数据的数组
        //告诉OpenGL，它可以在缓冲区vertexData中找到a_Position对应的数据
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        //为位置属性，使能顶点数组
        glEnableVertexAttribArray(aPositionLocation);

        //当OpenGL开始读入颜色属性时，我们要它从第一个颜色属性开始，而不是第一个位置属性
        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);
        //为颜色属性，使能顶点数组
        glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //更新着色器代码中的u_Color的值
//        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);//绘制三角形扇

//        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);

//        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);

//        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
