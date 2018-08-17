package com.lzp.day4;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextResourceReader {
    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder builder = new StringBuilder();

        try {
            InputStream is = context.getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                builder.append(tmp);
                builder.append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("read resourceId error", e);
        }
        return builder.toString();
    }
}
