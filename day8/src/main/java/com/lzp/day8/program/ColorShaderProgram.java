package com.lzp.day8.program;

import android.content.Context;
import android.opengl.GLES20;

import com.lzp.day8.R;
import com.lzp.day8.utils.Constants;

public class ColorShaderProgram extends ShaderProgram {
    private final int uMatrixLocation;
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context) {
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shadder);

        uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX);
        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(programId, A_COLOR);
    }

    public void setUniforms(float[] matrix) {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }

    public int getPositionAttributeLocation() {
        return aPositionLocation;
    }

    public int getColorAttributeLocation() {
        return aColorLocation;
    }
}
