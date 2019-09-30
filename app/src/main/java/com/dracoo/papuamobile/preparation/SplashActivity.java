package com.dracoo.papuamobile.preparation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dracoo.papuamobile.LoginActivity;
import com.dracoo.papuamobile.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        },3000);
    }
}
