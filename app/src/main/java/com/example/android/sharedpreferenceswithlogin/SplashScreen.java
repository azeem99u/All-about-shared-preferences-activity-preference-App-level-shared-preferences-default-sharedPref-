package com.example.android.sharedpreferenceswithlogin;

import static com.example.android.sharedpreferenceswithlogin.App.LOGIN_STATUS_KEY;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Handler handler = new Handler(getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        boolean isLogin = App.mySharedPreferences.getBoolean(LOGIN_STATUS_KEY,false);
                        if (isLogin){
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }else {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        finish();
                    }
                });
            }
        }).start();
    }
}