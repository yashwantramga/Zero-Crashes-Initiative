package com.example.mapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);
        Glide.with(this)
                .asGif()
                .override(500, 500) // Load a lower resolution version of the GIF
                .load(R.drawable.model)
                .into(img);
        //mover
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Mapactivity.class);
                startActivity(intent);
                finish();
            }
        }, 8500);


    }
}
