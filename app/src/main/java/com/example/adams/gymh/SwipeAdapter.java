package com.example.adams.gymh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by adams on 08.01.2017.
 */

public class SwipeAdapter extends FragmentPagerAdapter {

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }


    private Fragment mF1, mF2;

    public void setFragments(Fragment f1, Fragment f2) {

        mF1 = f1;
        mF2 = f2;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: {
                return mF1;
            }

            case 1: {
                return mF2;
            }

            default: {
                return null;
            }

        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
