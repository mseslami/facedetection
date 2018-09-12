package me.littlecheesecake.cropimagelayout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.opencv.android.Utils;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import retrofit2.Call;
import retrofit2.Response;

public class Menuactivity extends AppCompatActivity implements crop.TextClicked {
    public static NavigationTabBar navigationTabBar;
    public static ViewPager viewPager;


    public static int pixx1, pixx2, pixy1, pixy2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuactivity);


//        LinearLayout container = (LinearLayout) findViewById(R.id.background);
//        AnimationDrawable anim = (AnimationDrawable) container.getBackground();
//        anim.setEnterFadeDuration(1700);
//        anim.setExitFadeDuration(1700);
//        anim.start();


        initUI();

    }

    public void initUI() {


        viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        setupViewPager(viewPager);

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        navigationTabBar.setBackgroundColor(Color.RED);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.searchicon),
                        Color.parseColor("#55000000"))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("search")
//                        .badgeTitle("hi hi")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.facedetection),
                        Color.parseColor("#55000000"))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("camera")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.apa),
                        Color.parseColor("#55000000"))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("about us")
//                        .badgeTitle("hi hi")
                        .build()
        );
        navigationTabBar.setIsBadged(false);
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 1);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Menuactivity.this, "navigation tab bar is clicked", Toast.LENGTH_SHORT).show();

            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();


            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }


    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Searchfragment(), "");
        viewPagerAdapter.addFragment(new Mainfragment(), "");
        viewPagerAdapter.addFragment(new aboutusfragment(), "");
        viewPager.setAdapter(viewPagerAdapter);

    }


    @Override
    public void sendText(int x1, int y1, int x2, int y2) {
        pixx1 = x1;
        pixx2 = x2;
        pixy1 = y1;
        pixy2 = y2;

    }
}


