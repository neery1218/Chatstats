package com.radiance.chatstats;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neerajen on 12/03/2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private TotalMessagesFragment totalMessagesFragment;
    private InitiateCountFragment initiateCountFragment;
    private ResponseTimeFragment responseTimeFragment;
    private SummaryFragment summaryFragment;
    private EmoticonsFragment emoticonsFragment;
    private MessageLengthFragment messageLengthFragment;
    private HourFrequencyFragment hourFrequencyFragment;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
        totalMessagesFragment = new TotalMessagesFragment();
        initiateCountFragment = new InitiateCountFragment();
        responseTimeFragment = new ResponseTimeFragment();
        emoticonsFragment = new EmoticonsFragment();
        hourFrequencyFragment = new HourFrequencyFragment();
        fragments.add(totalMessagesFragment);
        fragments.add(initiateCountFragment);
        fragments.add(responseTimeFragment);
        fragments.add(emoticonsFragment);
        fragments.add(hourFrequencyFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
