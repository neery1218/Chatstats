package com.radiance.chatstats;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;


public class InitiateCountFragment extends Fragment implements FragmentLifeCycle {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView text;
    private StatPoint initiateCount;
    private PieChart pieChart;


    public InitiateCountFragment() {
        // Required empty public constructor
    }

    public static InitiateCountFragment newInstance(String param1, String param2) {
        InitiateCountFragment fragment = new InitiateCountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateCount = StatsFragment.getAnalytics().getInitiateCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_initiate_count, container, false);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        text = (TextView) view.findViewById(R.id.initiateCountText);
        Display display = getActivity().getWindowManager().getDefaultDisplay();

        setPieGraph();
        return view;
    }

    public void setPieGraph() {//for initiate Count

        //  pieChart.setUsePercentValues(true);

        // change the color of the center-hole
        pieChart.setHoleColor(Color.rgb(255, 255, 255));
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(50f);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);
        pieChart.setTransparentCircleRadius(40f);

        pieChart.setRotationEnabled(false);

        pieChart.setCenterText("" + ((int) (initiateCount.getSent() * 1000 / (initiateCount.getSent() + initiateCount.getReceived()))) * 1.0 / 10 + "%");
        pieChart.setCenterTextTypeface(MainActivity.oswaldLight);
        pieChart.setCenterTextSize(48f);
        pieChart.setCenterTextColor(Color.WHITE);


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
       /* double sent = StatsFragment.analytics.getInitiateCount().getSent();
        double received = StatsFragment.analytics.getInitiateCount().getReceived();
        *///data
        yVals1.add(new Entry((float) initiateCount.getSent(), 0));
        yVals1.add(new Entry((float) initiateCount.getReceived(), 1));
        text.setTypeface(MainActivity.typeFaceRegular);
        text.setText("Conversations Started" + "\n\n");
        text.setTypeface(MainActivity.oswaldLight);
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        text.startAnimation(fadeIn);
        fadeIn.setDuration(1000);
        fadeIn.setFillAfter(true);
        // text.setText("You start " +((int)(sent*1000/(sent+received)))*1.0/10 + "% of all conversations!");

        text.setTextSize(36f);
        text.setTextColor(Color.WHITE);
        //text.setHeight();
        //text.setHeight()

        //labels
        xVals.add("");
        xVals.add("");

        //title
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(0f);

        // colors for stuff
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ColorTemplate.VORDIPLOM_COLORS[0]);
        colors.add(Color.GRAY);
        // for (int c : ColorTemplate.VORDIPLOM_COLORS)
        //  colors.add(c);

        /*for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);*/
/*
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);*/

        /*for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);*/

       /* for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/
        //ColorTemplate.
        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);


        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(0f);


        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(MainActivity.typeFaceRegular);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();


        //pieChart.spin(2000, 0, 360);
        pieChart.setDescription("");
        Legend l = pieChart.getLegend();
        l.setEnabled(false);
        /*l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);*/
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

    }

    @Override
    public void onResumeFragment() {
        pieChart.animateXY(1500, 1500);
    }
}
