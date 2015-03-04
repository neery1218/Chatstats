package com.radiance.chatstats;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Neerajen on 02/03/2015.
 */
public class StatsPagerAdapter extends FragmentPagerAdapter {
    TotalMessagesFragment totalMessagesFragment;
    InitiateCountFragment initiateCountFragment;
    ResponseTimeFragment responseTimeFragment;
    SummaryFragment summaryFragment;
    EmoticonsFragment emoticonsFragment;
    MessageLengthFragment messageLengthFragment;


    public StatsPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (totalMessagesFragment != null)
                    return totalMessagesFragment;
                else {
                    totalMessagesFragment = new TotalMessagesFragment();
                    return totalMessagesFragment;
                }
            case 1:
                if (initiateCountFragment != null)
                    return initiateCountFragment;
                else {
                    initiateCountFragment = new InitiateCountFragment();
                    return initiateCountFragment;
                }
            case 2:
                if (responseTimeFragment != null)
                    return responseTimeFragment;
                else {
                    responseTimeFragment = new ResponseTimeFragment();
                    return responseTimeFragment;
                }
            case 3:
                if (messageLengthFragment != null)
                    return messageLengthFragment;
                else {
                    messageLengthFragment = new MessageLengthFragment();
                    return messageLengthFragment;
                }
            case 4:
                if (summaryFragment != null)
                    return summaryFragment;
                else {
                    summaryFragment = new SummaryFragment();
                    return summaryFragment;
                }
            case 5:
                if (emoticonsFragment != null)
                    return emoticonsFragment;
                else {
                    emoticonsFragment = new EmoticonsFragment();
                    return emoticonsFragment;
                }
            default:
                return new Fragment();


        }

    }

    @Override
    public int getCount() {
        return 6;
    }
}
