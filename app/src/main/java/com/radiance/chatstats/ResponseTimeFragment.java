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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ResponseTimeFragment extends Fragment implements FragmentLifeCycle {
    private HorizontalBarChart responseTimeChart;
    private HorizontalBarChart messageLengthChart;
    private StatPoint responseTime;
    private StatPoint avgMessageLength;
    private TextView responseTimeTextView;
    private TextView responseTimeStats;
    private TextView messageLengthTextView;
    private TextView messageLengthStats;
    private int maxValResponseTime;
    private int maxValAvgMessageLength;
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
        maxValResponseTime = (int)(Math.max(responseTime.getReceived(),responseTime.getSent())/5)*5+5;
        maxValAvgMessageLength = (int)(Math.max(avgMessageLength.getReceived(),avgMessageLength.getSent())/5)*5+5;
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

        responseTimeStats= (TextView) view.findViewById(R.id.responseLengthStats);
        responseTimeStats.setText("You: "+getHMS(responseTime.getSent())+ "\nThem: " + getHMS(responseTime.getReceived()));
        responseTimeStats.setTextSize(24f);
        responseTimeStats.setTypeface(MainActivity.oswaldLight);
        responseTimeStats.setTextColor(Color.WHITE);

        messageLengthTextView = (TextView) view.findViewById(R.id.messageLengthTitleView);
        messageLengthTextView.setText("Average Message Length");
        messageLengthTextView.setTextSize(36f);
        messageLengthTextView.setTypeface(MainActivity.oswaldLight);
        messageLengthTextView.setTextColor(Color.WHITE);

        messageLengthStats = (TextView) view.findViewById(R.id.messageLengthStats);
        messageLengthStats.setText("You: " + (int)(avgMessageLength.getSent()*10)/10.0 + " words\nThem: " + (int)(avgMessageLength.getReceived()*10)/10.0 + " words");
        messageLengthStats.setTextSize(24f);
        messageLengthStats.setTypeface(MainActivity.oswaldLight);
        messageLengthStats.setTextColor(Color.WHITE);

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
        xl.setTextColor(Color.WHITE);
        xl.setDrawLabels(false);
        // xl.setEnabled(false);

        YAxis yl = messageLengthChart.getAxisLeft();
        yl.setTypeface(MainActivity.typeFaceRegular);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisLineColor(Color.WHITE);
        yl.setAxisLineWidth(2f);
        yl.setTextColor(Color.WHITE);


        messageLengthChart.getAxisLeft().setValueFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "|";
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
        dataSets.get(0).setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        dataSets.add(set2);
        dataSets.get(1).setColor(Color.GRAY);


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
        xl.setAxisLineColor(Color.WHITE);
        xl.setAxisLineWidth(2f);
        xl.setTextColor(Color.WHITE);
        // xl.setEnabled(false);

        YAxis yl = responseTimeChart.getAxisLeft();
        yl.setTypeface(MainActivity.typeFaceRegular);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setDrawLabels(true);
        yl.setAxisLineColor(Color.WHITE);
        yl.setAxisLineWidth(2f);
        yl.setTextColor(Color.WHITE);
        responseTimeChart.getAxisLeft().setValueFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value)
                    {
                        return "|";
                       /* Log.d("TAG", value+"");
                        if (value<Math.max(responseTime.getReceived(),responseTime.getSent()))
                        {
                            return "";
                        }
                        else
                        {
                            return maxValResponseTime + "";
                        }*/
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

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.get(0).setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        dataSets.add(set2);
        dataSets.get(1).setColor(Color.GRAY);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(0f);

        // data.setValueTypeface(MainActivity.typeFaceRegular);
        responseTimeChart.setData(data);
        responseTimeChart.setTouchEnabled(false);
        responseTimeChart.getLegend().setEnabled(false);
    }

    private String getHMS (double timeInMilliSeconds)
    {
        long seconds = (long) timeInMilliSeconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return hours % 24 + "h, " + minutes % 60 + "m, " + seconds % 60 + "s";
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
