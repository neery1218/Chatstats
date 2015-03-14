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


public class ResponseTimeFragment extends Fragment implements FragmentLifeCycle {
    private HorizontalBarChart responseTimeChart;
    private HorizontalBarChart messageLengthChart;
    private StatPoint responseTime;
    private StatPoint avgMessageLength;
    private TextView responseTimeTextView;
    private TextView messageLengthTextView;
    private boolean animate;

    public ResponseTimeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ResponseTimeFragment newInstance(String param1, String param2) {
        ResponseTimeFragment fragment = new ResponseTimeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        responseTime = StatsFragment.getAnalytics().getResponseTime();
        avgMessageLength = StatsFragment.getAnalytics().getAvgMessageLengthWords();
        animate = true;
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_response_time, container, false);
      /*  titleTextView = (TextView) view.findViewById(R.id.titleView);
        titleTextView.setText("Average Response Time");*/

        responseTimeTextView = (TextView) view.findViewById(R.id.responseLengthTitleView);
        responseTimeTextView.setText("Average Response Time");
        responseTimeTextView.setTextSize(36f);
        responseTimeTextView.setTypeface(MainActivity.oswaldLight);
        responseTimeTextView.setTextColor(Color.WHITE);
        responseTimeChart = (HorizontalBarChart) view.findViewById(R.id.responseTimeChart);

        messageLengthTextView = (TextView) view.findViewById(R.id.messageLengthTitleView);
        messageLengthTextView.setText("Average Message Length");
        messageLengthTextView.setTextSize(36f);
        messageLengthTextView.setTypeface(MainActivity.oswaldLight);
        messageLengthTextView.setTextColor(Color.WHITE);

        responseTimeChart = (HorizontalBarChart) view.findViewById(R.id.responseTimeChart);
        messageLengthChart = (HorizontalBarChart) view.findViewById(R.id.messageLengthChart);


        setResponseTimeGraph();
        setMessageLengthGraph();
        return view;
    }

    public void setMessageLengthGraph() {
        messageLengthChart.setTouchEnabled(false);
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
    }

    public void setResponseTimeGraph() {

        responseTimeChart.setDrawBarShadow(false);
        responseTimeChart.setDrawValueAboveBar(true);
        responseTimeChart.setMaxVisibleValueCount(60);
        responseTimeChart.setPinchZoom(false);
        responseTimeChart.setDoubleTapToZoomEnabled(false);
        responseTimeChart.setDescription("");


        // responseTimeChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        responseTimeChart.setDrawGridBackground(false);


        XAxis xl = responseTimeChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(MainActivity.typeFaceRegular);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);
        xl.setDrawLabels(false);
        // xl.setEnabled(false);

        YAxis yl = responseTimeChart.getAxisLeft();
        yl.setTypeface(MainActivity.typeFaceRegular);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setDrawLabels(true);
        responseTimeChart.getAxisLeft().setValueFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "" + (int) value;
                    }

                }
        );


        YAxis yr = responseTimeChart.getAxisRight();
        yr.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("a");

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry((float) responseTime.getSent(), 0));
        yVals2.add(new BarEntry((float) responseTime.getReceived(), 0));

        BarDataSet set1 = new BarDataSet(yVals1, "");
        BarDataSet set2 = new BarDataSet(yVals2, "");
        // set1.setBarSpacePercent(0f);
        //TODO: Add color to graphs
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(0f);

        // data.setValueTypeface(MainActivity.typeFaceRegular);
        responseTimeChart.setData(data);
        responseTimeChart.setTouchEnabled(false);
        responseTimeChart.getLegend().setEnabled(false);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPauseFragment() {

        animate = false;
    }

    @Override
    public void onResumeFragment() {
        if (animate) {
            responseTimeChart.animateY(1000);
            messageLengthChart.animateY(1000);
            animate = false;
        }


    }
}
