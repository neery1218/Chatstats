package com.radiance.chatstats;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Neerajen on 02/03/2015.
 */
public class StatsPagerAdapter extends FragmentPagerAdapter {
 /*   TotalMessagesFragment totalMessagesFragment;
    InitiateCountFragment initiateCountFragment;
    ResponseTimeFragment responseTimeFragment;
    SummaryFragment summaryFragment;
    EmoticonsFragment emoticonsFragment;
    MessageLengthFragment messageLengthFragment;*/


    public StatsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TotalMessagesFragment();
            case 1:
                return new InitiateCountFragment();
            case 2:
                return new ResponseTimeFragment();
            case 3:
                return new MessageLengthFragment();
            case 4:
                return new EmoticonsFragment();
            default:
                return new Fragment();


        }

    }

    @Override
    public int getCount() {
        return 5;
    }
}
