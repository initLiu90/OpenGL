package com.lzp.day8.utils;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderHelper {
    /**
     * 创建shader
     *
     * @param shaderSourceCode
     * @return
     */
    public static int createVertexShader(String shaderSourceCode) {
        return createShader(GLES20.GL_VERTEX_SHADER, shaderSourceCode);
    }

    public static int createFragmentShader(String shaderSourceCode) {
        return createShader(GLES20.GL_FRAGMENT_SHADER, shaderSourceCode);
    }

    private static int createShader(int type, String shaderSourceCode) {
        int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            Log.e("Test", "create shader object failed");
            return 0;
        }
        GLES20.glShaderSource(shaderObjectId, shaderSourceCode);
        GLES20.glCompileShader(shaderObjectId);

        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        Log.e("Test", "Results of compiling source:\n" + shaderSourceCode + "\n:" +
                GLES20.glGetShaderInfoLog(shaderObjectId));
        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderObjectId);
            Log.e("Test", "compile failed");
            return 0;
        }
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        int programId = GLES20.glCreateProgram();
        if (programId == 0) {
            Log.e("Test", "create program failed");
            return 0;
        }
        GLES20.glAttachShader(programId, vertexShaderId);
        GLES20.glAttachShader(programId, fragmentShaderId);

        GLES20.glLinkProgram(programId);

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programId);
            Log.e("Test", "link program failed");
            return 0;
        }
        return programId;
    }

    public static boolean validateProgram(int programId) {
        GLES20.glValidateProgram(programId);

        int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        Log.e("Test", "Results of validating program:" + validateStatus[0] + "\nLog" +
                GLES20.glGetProgramInfoLog(programId));
        return validateStatus[0] != 0;
    }

    public static int createProgram(String vertexShaderSource, String fragmentShaderSource) {
        int vertexShaderId = createVertexShader(vertexShaderSource);
        int fragmentShaderId = createFragmentShader(fragmentShaderSource);

        int programId = linkProgram(vertexShaderId, fragmentShaderId);
        validateProgram(programId);
        return programId;
    }
}
