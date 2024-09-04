package com.example.chatclone;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Splash extends AppCompatActivity {
    ImageView imgView;
    TextView text;
    Animation topAnime,bottomAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgView = findViewById(R.id.imageSplash);
        text = findViewById(R.id.textSplash);

        topAnime = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnime = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imgView.setAnimation(topAnime);
        text.setAnimation(bottomAnime);
//        It is used to hide the Top Bar which appears in the app
//        getSupportActionBar().hide();

//        Handler().postDelayed is used for :
//        Giving the Delay
//        Going to new Page/Intent
//        postDelayed(intent,delay in ms)

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this , Login.class);
                startActivity(i);
                finish();
            }
        },4000);

    }
}