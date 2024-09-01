package com.example.chatclone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        It is used to hide the Top Bar which appears in the app
//        getSupportActionBar().hide();

//        Handler().postDelayed is used for :
//        Giving the Delay
//        Going to new Page/Intent
//        postDelayed(intent,delay in ms)

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this ,MainActivity.class);
                startActivity(i);
                finish();
            }
        },4000);

    }
}