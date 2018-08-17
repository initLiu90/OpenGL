package com.lzp.day4;

import static android.opengl.GLES20.*;

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    /**
     * 编译顶点着色器
     * @param shaderCode
     * @return
     */
    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    /**
     * 编译片段着色器
     * @param shaderCode
     * @return
     */
    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    /**
     * 编译着色器
     * @param type 着色器类型
     * @param shaderCode
     * @return
     */
    private static int compileShader(int type, String shaderCode) {

    }
}
