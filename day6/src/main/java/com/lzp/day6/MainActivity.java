package com.lzp.day6;

import android.app.ActivityManager;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GLSurfaceView mGlSurfaceView;
    boolean mRenderSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGlSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);
        if (supportGLES2()) {
            mGlSurfaceView.setEGLContextClientVersion(2);
            mGlSurfaceView.setRenderer(new AirHockeyRender(this));
            mRenderSet = true;
        } else {
            Toast.makeText(this, "unsupport opengl es 2.0", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRenderSet) {
            mGlSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRenderSet) {
            mGlSurfaceView.onResume();
        }
    }

    private boolean supportGLES2() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        return activityManager.getDeviceConfigurationInfo().reqGlEsVersion >= 0x20000;
    }
}
