package com.lzp.day8.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextResourceReader {
    public static String readTextFileFromResource(Context context, int resId) {
        StringBuilder body = new StringBuilder();
        try {
            InputStream is = context.getResources().openRawResource(resId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                body.append(buffer);
                body.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body.toString();
    }
}
