package com.radiance.chatstats;

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

import java.util.ArrayList;


public class MessageLengthFragment extends Fragment {
    private HorizontalBarChart barChart;
    private TextView titleTextView;
    private TextView sentLengthTextView;
    private TextView receivedLengthTextView;
    private TextView sentNameTextView;
    private TextView receivedNameTextView;
    private StatPoint avgMessageLength;


    public MessageLengthFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static MessageLengthFragment newInstance(String param1, String param2) {
        MessageLengthFragment fragment = new MessageLengthFragment();

        return fragment;
    }

    public void setBarGraph() {

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);

        // barChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        barChart.setDrawGridBackground(false);

        // mChart.setDrawYLabels(false);

        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(MainActivity.typeFaceRegular);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);
        xl.setAxisLineColor(Color.WHITE);
        xl.setAxisLineWidth(2f);
        // xl.setEnabled(false);

        YAxis yl = barChart.getAxisLeft();
        yl.setTypeface(MainActivity.typeFaceRegular);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisLineColor(Color.WHITE);
        yl.setAxisLineWidth(2f);
        barChart.getAxisLeft().setValueFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "" + (int) value;
                    }

        }
        );


        YAxis yr = barChart.getAxisRight();
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
        barChart.setData(data);
        barChart.animateY(1000);
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
        barChart = (HorizontalBarChart) view.findViewById(R.id.barChart);

        sentLengthTextView = (TextView) view.findViewById(R.id.sentLength);
        receivedLengthTextView = (TextView) view.findViewById(R.id.receivedLength);
        sentNameTextView = (TextView) view.findViewById(R.id.sentName);
        receivedNameTextView = (TextView) view.findViewById(R.id.receivedName);

        receivedNameTextView.setText("" + StatsFragment.getName() + ": ");
        sentNameTextView.setText("You: ");

        sentLengthTextView.setText(((int) (avgMessageLength.getSent() * 10) * 1.0) / 10 + " words");
        receivedLengthTextView.setText(((int) (avgMessageLength.getReceived() * 10) * 1.0) / 10 + " words");

        receivedNameTextView.setTypeface(MainActivity.typeFaceRegular);
        sentNameTextView.setTypeface(MainActivity.typeFaceRegular);
        receivedLengthTextView.setTypeface(MainActivity.typeFaceBold);
        sentLengthTextView.setTypeface(MainActivity.typeFaceBold);

        receivedLengthTextView.setTextSize(30f);
        sentLengthTextView.setTextSize(30f);
        receivedNameTextView.setTextSize(30f);
        sentNameTextView.setTextSize(30f);


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
