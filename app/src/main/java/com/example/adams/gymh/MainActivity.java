package com.example.adams.gymh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {

    ViewPager mViewPager;
    SwipeAdapter swipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.vPage);
        swipeAdapter=new SwipeAdapter(getSupportFragmentManager());

        Fragment time=new TimeFragment();
        Fragment nut=new NutritionFragment();
        swipeAdapter.setFragments(time,nut);

        mViewPager.setAdapter(swipeAdapter);
    }
    public void stopper(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TimeFragment tm= (TimeFragment) swipeAdapter.getItem(0);
                tm.setTextTimeView(time);
            }
        });
    }
}
