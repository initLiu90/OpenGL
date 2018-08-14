package com.lzp.opengl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lzp.opengl.day1.FirstActivity;
import com.lzp.opengl.day2.AirHockeyActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.day1).setOnClickListener(this);
        findViewById(R.id.day2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.day1:
                Intent intent = new Intent(this, FirstActivity.class);
                startActivity(intent);
                break;
            case R.id.day2:
                intent = new Intent(this, AirHockeyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
