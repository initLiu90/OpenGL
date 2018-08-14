package com.lzp.opengl.day2;

import android.util.Log;

import static android.opengl.GLES20.*;

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);//创建一个新的着色器对象.
        if (shaderObjectId == 0) {
            Log.e(TAG, "Could not create new shader.");
            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);//告诉OpenGL读入字符串shaderCode定义的源代码，并把它与shaderObjectId所引用的着色器对象关联起来。
        glCompileShader(shaderObjectId);//编译着色器。告诉OpenGL编译先前上传到shaderObjectId的源代码

        final int[] compileStates = new int[1];
        //告诉OpenGL读取与shaderObjectId关联的编译状态，并把它写入compileStates的第0个元素
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStates, 0);

        //取出着色器日志
        Log.e(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                + glGetShaderInfoLog(shaderObjectId));

        //验证编译状态，并返回着色器对象id
        if (compileStates[0] == 0) {
            glDeleteShader(shaderObjectId);
            Log.e(TAG, "Compilation of shader failed");
            return 0;
        }
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        //新建程序对象并附上着色器
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            Log.e(TAG, "Could not create new Program");
            return 0;
        }

        //把顶点着色器和片段着色器都附加到程序对象上。
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        //链接程序
        glLinkProgram(programObjectId);
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        Log.e(TAG, "Results of linking program:\n" + glGetProgramInfoLog(programObjectId));

        //验证链接状态，并返回程序对象id
        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            Log.e(TAG, "Linking of program failed");
            return 0;
        }
        return programObjectId;
    }

    /**
     * 验证OpenGL程序的对象
     *
     * @param programObjectId
     * @return
     */
    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.e(TAG, "Results of validating program: " + validateStatus[0] + "\nLog:" + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }
}
