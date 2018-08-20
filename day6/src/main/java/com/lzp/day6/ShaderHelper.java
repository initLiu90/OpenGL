package com.lzp.day6;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderHelper {
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    public static int compileShader(int type, String shaderCode) {
        int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            Log.e("Test", "Create shader error");
            return 0;
        }

        GLES20.glShaderSource(shaderObjectId, shaderCode);
        GLES20.glCompileShader(shaderObjectId);

        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        Log.e("Test", "Results of compiling source:\n" + shaderCode + "\n:" +
                GLES20.glGetShaderInfoLog(shaderObjectId));

        if (compileStatus[0] == 0) {
            GLES20.glDeleteShader(shaderObjectId);
            Log.e("Test", "Compilation of shader failed");
            return 0;
        }
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = GLES20.glCreateProgram();
        if (programObjectId == 0) {
            Log.e("Test", "Could not create new program");
            return 0;
        }

        GLES20.glAttachShader(programObjectId, vertexShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);

        GLES20.glLinkProgram(programObjectId);

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);

        Log.e("Test", "Results of linking program:\n" + GLES20.glGetProgramInfoLog(programObjectId));

        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId);
            Log.e("Test", "Linking of program failed");
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programId) {
        GLES20.glValidateProgram(programId);

        int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);
        Log.e("Test", "Results of validating program:" + validateStatus[0] + "\nLog" +
                GLES20.glGetProgramInfoLog(programId));
        return validateStatus[0] != 0;
    }
}
