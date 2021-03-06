package com.radianceTOPS.chatstats;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.radiance.chatstats.R;

import java.util.ArrayList;


public class MessageLengthFragment extends Fragment {
    private HorizontalBarChart messageLengthChart;
    private TextView titleTextView;
    private TextView sentLengthTextView;
    private TextView receivedLengthTextView;
    private TextView sentNameTextView;
    private TextView receivedNameTextView;
    private StatPoint avgMessageLength;


    public MessageLengthFragment() {
        // Required empty public constructor
    }

    public static MessageLengthFragment newInstance(String param1, String param2) {
        MessageLengthFragment fragment = new MessageLengthFragment();

        return fragment;
    }

    public void setBarGraph() {

        messageLengthChart.setDrawBarShadow(false);
        messageLengthChart.setDrawValueAboveBar(true);
        messageLengthChart.setDescription("");
        messageLengthChart.setMaxVisibleValueCount(60);
        messageLengthChart.setPinchZoom(false);

        // messageLengthChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        messageLengthChart.setDrawGridBackground(false);

        // mChart.setDrawYLabels(false);

        XAxis xl = messageLengthChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(MainActivity.typeFaceRegular);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);
        xl.setAxisLineColor(Color.WHITE);
        xl.setAxisLineWidth(2f);
        xl.setDrawLabels(false);
        // xl.setEnabled(false);

        YAxis yl = messageLengthChart.getAxisLeft();
        yl.setTypeface(MainActivity.typeFaceRegular);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisLineColor(Color.WHITE);
        yl.setAxisLineWidth(2f);


        messageLengthChart.getAxisLeft().setValueFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "" + (int) value;
                    }

                }
        );


        YAxis yr = messageLengthChart.getAxisRight();
        yr.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("a");

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry((float) avgMessageLength.getSent(), 0));
        yVals2.add(new BarEntry((float) avgMessageLength.getReceived(), 0));

        BarDataSet set1 = new BarDataSet(yVals1, "");
        BarDataSet set2 = new BarDataSet(yVals2, "");
        // set1.setBarSpacePercent(0f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(0f);

        // data.setValueTypeface(MainActivity.typeFaceRegular);
        messageLengthChart.setData(data);
        messageLengthChart.getLegend().setEnabled(false);
        messageLengthChart.animateY(1000);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avgMessageLength = StatsFragment.getAnalytics().getAvgMessageLengthWords();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_length, container, false);
        titleTextView = (TextView) view.findViewById(R.id.titleView);
        titleTextView.setText("Average Message Length");
        titleTextView.setTextSize(36);
        titleTextView.setTypeface(MainActivity.oswaldLight);
        titleTextView.setTextColor(Color.WHITE);
        messageLengthChart = (HorizontalBarChart) view.findViewById(R.id.barChart);

        sentLengthTextView = (TextView) view.findViewById(R.id.sentLength);
        receivedLengthTextView = (TextView) view.findViewById(R.id.receivedLength);
        sentNameTextView = (TextView) view.findViewById(R.id.sentName);
        receivedNameTextView = (TextView) view.findViewById(R.id.receivedName);

        receivedNameTextView.setText("Them: ");
        sentNameTextView.setText("You: ");

        sentLengthTextView.setText(((int) (avgMessageLength.getSent() * 10) * 1.0) / 10 + " words");
        receivedLengthTextView.setText(((int) (avgMessageLength.getReceived() * 10) * 1.0) / 10 + " words");

        receivedNameTextView.setTypeface(MainActivity.oswaldLight);
        sentNameTextView.setTypeface(MainActivity.oswaldLight);
        receivedLengthTextView.setTypeface(MainActivity.oswaldRegular);
        sentLengthTextView.setTypeface(MainActivity.oswaldRegular);

        receivedLengthTextView.setTextSize(48f);
        sentLengthTextView.setTextSize(48f);
        receivedNameTextView.setTextSize(48f);
        sentNameTextView.setTextSize(48f);


        setBarGraph();
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
