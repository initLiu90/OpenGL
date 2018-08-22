package com.lzp.day7.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.lzp.day7.R;

public class ColorShaderProgram extends ShaderProgram {
    private final int uMatrixLocation;

    private final int aPostionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        aPostionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation() {
        return aPostionLocation;
    }

    public int getColorAttributeLocation() {
        return aColorLocation;
    }
}
