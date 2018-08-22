package com.lzp.day7.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import static android.opengl.GLES20.*;

public class TextureHelper {
    /**
     * 把一个图像文件的数据加载到一个OpenGL的纹理中
     *
     * @param context
     * @param resourceId
     * @return 纹理ID
     */
    public static int loadTexture(Context context, int resourceId) {
        final int[] textureObjectIds = new int[1];
        glGenTextures(1, textureObjectIds, 0);

        if (textureObjectIds[0] == 0) {
            Log.e("Test", "Could not generate a new OpenGL texture object");
            return 0;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        if (bitmap == null) {
            Log.e("Test", "ResourceID " + resourceId + " could not be decoded");
            glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }

        //告诉OpenGL后面的纹理调用应该应用于这个纹理对象
        glBindTexture(GL_TEXTURE_2D,textureObjectIds[0]);

        //设置默认的纹理过滤参数
        //GL_TEXTURE_MIN_FILTER--->是指缩小的情况
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
        //GL_TEXTURE_MAG_FILTER---->是指放大的情况
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);

        //告诉OpenGL读入bitmap定义的位图数据，并把它复制到当前绑定的纹理对象
        GLUtils.texImage2D(GL_TEXTURE_2D,0,bitmap,0);
        bitmap.recycle();

        //生成MIP贴图
        glGenerateMipmap(GL_TEXTURE_2D);

        //完成了纹理加载后，需要解除与这个纹理的绑定，这样我们就不会用其他纹理方法调用意外地改变这个纹理。传递0给glBindTexture就与当前的纹理解除绑定了
        glBindTexture(GL_TEXTURE_2D,0);

        return textureObjectIds[0];
    }
}
