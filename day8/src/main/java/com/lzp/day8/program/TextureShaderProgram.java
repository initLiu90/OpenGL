package com.lzp.day8.program;

import android.content.Context;

import com.lzp.day8.R;

public class TextureShaderProgram extends ShaderProgram {
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    private final int aPositionLocation;
    private final int aTextureCoordinateLocation;

    public TextureShaderProgram(Context context){
        super(context, R.raw.texture_vertex_shader,R.raw.texture_fragment_shader);




    }
}
