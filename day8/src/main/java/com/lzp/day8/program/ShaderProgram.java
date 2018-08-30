package com.lzp.day8.program;

import android.content.Context;
import android.opengl.GLES20;

import com.lzp.day8.utils.ShaderHelper;
import com.lzp.day8.utils.TextResourceReader;

public class ShaderProgram {
    public static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    public static final String A_POSITION = "a_Position";
    public static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected int programId;

    public ShaderProgram(Context context, int vertexShaderResouceId, int fragmentShaderResourceId) {
        programId = ShaderHelper.createProgram(TextResourceReader.readTextFileFromResource(context, vertexShaderResouceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));
    }

    public void useProgram() {
        GLES20.glUseProgram(programId);
    }
}
