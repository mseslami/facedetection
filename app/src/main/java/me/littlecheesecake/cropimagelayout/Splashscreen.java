package me.littlecheesecake.cropimagelayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.w3c.dom.Text;
//import com.hanks.htextview.HTextView;
//import com.hanks.htextview.HTextViewType;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        ImageView logosplash = (ImageView) findViewById(R.id.logoid);
        TextView apatxt = (TextView) findViewById(R.id.apatxt);
        YoYo.with(Techniques.FadeInDown).duration(1600).repeat(0).playOn(logosplash);
        YoYo.with(Techniques.FadeIn).duration(1700).repeat(0).playOn(apatxt);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.splashrel);
        AnimationDrawable anim = (AnimationDrawable) container.getBackground();
        anim.setEnterFadeDuration(900);
        anim.setExitFadeDuration(900);
        anim.start();


//        HTextView hTextView2 = (HTextView) findViewById(R.id.text2);
//        hTextView2.setAnimateType(HTextViewType.ANVIL);
//        hTextView2.animateText("مرکز تخصصی آپا دانشگاه قم"); // animate



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splashscreen.this, Menuactivity.class);
                startActivity(i);

                finish();
            }
        }, 3000);


    }
}
