package com.radianceTOPS.chatstats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radiance.chatstats.R;


public class TotalMessagesFragment extends Fragment implements FragmentLifeCycle {

    public TotalMessagesFragment() {
        // Required empty public constructor
    }

    public static TotalMessagesFragment newInstance(String param1, String param2) {
        TotalMessagesFragment fragment = new TotalMessagesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_total_messages, container, false);

        //Retrieve Textviews
        TextView totalMessages = (TextView) view.findViewById(R.id.total_messages);
        TextView totalMessagesNumber = (TextView) view.findViewById(R.id.total_messages_number);
        TextView totalConversations = (TextView) view.findViewById(R.id.total_conversations);
        TextView totalConversationsNumber = (TextView) view.findViewById(R.id.total_conversations_number);

        // StatsFragment.setLayoutColor("#ff1bbc9b");
        //Change text font size
        totalMessages.setTextSize(36);
        totalMessagesNumber.setTextSize(144);
        totalConversations.setTextSize(36);
        totalConversationsNumber.setTextSize(144);

        //Change text fonts
        totalMessages.setTypeface(MainActivity.oswaldLight);
        totalMessagesNumber.setTypeface(MainActivity.oswaldLight);
        totalConversations.setTypeface(MainActivity.oswaldLight);
        totalConversationsNumber.setTypeface(MainActivity.oswaldLight);

        //Change text colors
        totalMessages.setTextColor(Color.parseColor("#FFFFFF"));
        totalMessagesNumber.setTextColor(Color.parseColor("#FFFFFF"));
        totalConversations.setTextColor(Color.parseColor("#FFFFFF"));
        totalConversationsNumber.setTextColor(Color.parseColor("#FFFFFF"));

        //Set Background Colors
       /* totalMessages.setBackgroundColor(Color.parseColor("#1BBC9B"));
        totalMessagesNumber.setBackgroundColor(Color.parseColor("#1BBC9B"));
        totalConversations.setBackgroundColor(Color.parseColor("#1BBC9B"));
        totalConversationsNumber.setBackgroundColor(Color.parseColor("#1BBC9B"));*/


        //Set texts
        totalMessagesNumber.setText(StatsFragment.getAnalytics().getTotalMessages() + "");
        totalConversationsNumber.setText(StatsFragment.getAnalytics().getTotalConversations() + "");

        return view;
    }

    @Override
    public void onPauseFragment() {
    }

    @Override
    public void onResumeFragment() {
    }
}
