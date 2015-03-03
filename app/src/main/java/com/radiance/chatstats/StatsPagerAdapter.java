package com.radiance.chatstats;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Neerajen on 02/03/2015.
 */
public class StatsPagerAdapter extends FragmentPagerAdapter {

    public StatsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        TotalMessagesFragment test = new TotalMessagesFragment();

        return test;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
