package com.example.sofia.psptsp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);


        ImageView image= findViewById(R.id.icon);

        TextView text= findViewById(R.id.titulo);

        Animation aparece= AnimationUtils.loadAnimation(this, R.anim.aparece);

        image.startAnimation(aparece);
        text.startAnimation(aparece);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(Splash_Screen.this,Home.class );
                startActivity(intent);
            }
        }, 3000);
    }
}
