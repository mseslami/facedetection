package me.littlecheesecake.cropimagelayout;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        ImageView logosplash = (ImageView) findViewById(R.id.logoid);
        YoYo.with(Techniques.ZoomIn).duration(1500).repeat(0).playOn(logosplash);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, 2000);


    }
}
