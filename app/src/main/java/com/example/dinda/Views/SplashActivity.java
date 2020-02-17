package com.example.dinda.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dinda.R;
import com.example.dinda.Tabs.MenuActivity;

public class SplashActivity extends Activity {

    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_splash);

        //Inisialisasi Objek Splah Logo
        imgLogo = findViewById(R.id.splash_imgLogo);

        //Sumber Animasi
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadeout);

        //Mulai Animasi
        imgLogo.startAnimation(animation);

        //Detik Splash Screen
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 //Memanggil MainActivity
                 Intent panggil = new Intent(SplashActivity.this, MenuActivity.class);
                 startActivity(panggil);

                 //Splash Screen Hilang
                 finish();
             }
         },3000);
    }
}
