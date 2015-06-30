package com.radianceTOPS.chatstats;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Neerajen on 02/03/2015.
 */
public class StatsPagerAdapter extends FragmentPagerAdapter {
    private TotalMessagesFragment totalMessagesFragment;
    private InitiateCountFragment initiateCountFragment;
    private ResponseTimeFragment responseTimeFragment;
    private SummaryFragment summaryFragment;
    private EmoticonsFragment emoticonsFragment;
    private MessageLengthFragment messageLengthFragment;


    public StatsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StatsFragment.setLayoutColor("#ff1bbc9b");
                return new TotalMessagesFragment();
            case 1:
                StatsFragment.setLayoutColor("#ffaaaaaa");
                return new InitiateCountFragment();
            case 2:
                StatsFragment.setLayoutColor("#ffa483e6");
                return new ResponseTimeFragment();
            case 3:
                StatsFragment.setLayoutColor("#ff34495e");
                return new EmoticonsFragment();
            default:
                return new Fragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
