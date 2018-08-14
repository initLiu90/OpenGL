package com.lzp.opengl.day2;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lzp.opengl.R;

public class AirHockeyActivity extends AppCompatActivity{
    GLSurfaceView mGlSurfaceView;
    boolean rendererSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airhockey);
        mGlSurfaceView = (GLSurfaceView) findViewById(R.id.glsurfaceview);

        if (isSupportOpenGLES2()) {
            mGlSurfaceView.setEGLContextClientVersion(2);
            mGlSurfaceView.setRenderer(new AirHockeyRenderer(this));
            rendererSet = true;
        } else {
            Toast.makeText(this, "unsupport open gl es2", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rendererSet) {
            mGlSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) {
            mGlSurfaceView.onPause();
        }
    }

    private boolean isSupportOpenGLES2() {
        final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        return supportsEs2;
    }
}
