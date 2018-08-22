package com.lzp.day7.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextResourceReader {
    public static String readTextResource(Context context, int resourdeId) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream is = context.getResources().openRawResource(resourdeId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                content.append(buffer);
                content.append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("read resource:" + resourdeId + " error", e);
        }
        return content.toString();
    }
}
