package com.lzp.day4;

import android.util.Log;

import static android.opengl.GLES20.*;

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    /**
     * 编译顶点着色器
     *
     * @param shaderCode
     * @return
     */
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    /**
     * 编译片段着色器
     *
     * @param shaderCode
     * @return
     */
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * 创建着色器，并编译着色器代码
     * 1、根据着色器类型创建着色器
     * 2、将着色器代码和着色器管理
     * 3、编译着色器代码
     * 4、获取着色器编译状态
     * 5、返回着色器id
     *
     * @param type       着色器类型
     * @param shaderCode
     * @return
     */
    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);//创建一个新的着色器对象。
        if (shaderObjectId == 0) {
            Log.e("Test", "could not create new shader");
            return 0;
        }
        //告诉OpenGL读入字符串傻的人code定义的源代码，并把它与shaderObjectId所引用的着色器对象关联起来
        glShaderSource(shaderObjectId, shaderCode);
        //编译着色器代码
        glCompileShader(shaderObjectId);

        //告诉OpenGL读取与shaderObjectId关联的编译状态，并把它写入compileStates的第0各元素
        final int[] compileStates = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStates, 0);

        //取出着色器日志
        Log.e("Test", "Results of compiling source:" + "\n" + shaderCode + "\n:" +
                glGetShaderInfoLog(shaderObjectId));

        if (compileStates[0] == 0) {
            glDeleteShader(shaderObjectId);
            Log.e("Test", "Compilation of shader failed");
            return 0;
        }
        return shaderObjectId;
    }

    /**
     * 创建program，并且将顶点着色器和片段着色器关联到program上
     * 1、创建程序对象program
     * 2、把顶点着色器和片段着色器attach到program上
     * 3、链接program
     * 4、验证链接状态，返回program对象id
     *
     * @param vertexShaderId
     * @param fragmentShaderId
     * @return
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        //新建程序对象
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            Log.e("Test", "Could not create new program");
            return 0;
        }

        //把顶点着色器和片段着色器附加到program对象上。
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        //链接程序
        glLinkProgram(programObjectId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        Log.e("Test", "Results of linking program:\n" + glGetProgramInfoLog(programObjectId));

        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            Log.e("Test", "Linking of program failed");
            return 0;
        }
        return programObjectId;
    }

    /**
     * 验证OpenGL程序对象
     *
     * @param programObjectId
     * @return
     */
    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.e("Test", "Results of validating program:" + validateStatus[0] + "\nLog:" +
                glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }
}
