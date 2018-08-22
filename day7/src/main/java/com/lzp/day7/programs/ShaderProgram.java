package com.lzp.day7.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.lzp.day7.R;
import com.lzp.day7.util.ShaderHelper;
import com.lzp.day7.util.TextResourceReader;

public class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderObjectId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(TextResourceReader.readTextResource(context, vertexShaderObjectId),
                TextResourceReader.readTextResource(context, fragmentShaderResourceId));
    }

    public void useProgram() {
        GLES20.glUseProgram(program);
    }
}
