package com.radiance.chatstats;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;


public class InitiateCountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private PieChart pieChart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public InitiateCountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InitiateCountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InitiateCountFragment newInstance(String param1, String param2) {
        InitiateCountFragment fragment = new InitiateCountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_initiate_count, container, false);
        pieChart = (PieChart) view.findViewById(R.id.chart);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int height = point.y;
        pieChart.setMinimumHeight(height);
        //pieChart.setLayoutHeight
        Log.v("tag", Integer.toString(height));
        setPieGraph();
        return view;
    }

    public void setPieGraph() {//for initiate Count

        pieChart.setUsePercentValues(true);

        // change the color of the center-hole
        pieChart.setHoleColor(Color.rgb(255, 255, 255));
        //pieChart.setHoleColorTransparent(true);

        // typeFaceRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        pieChart.setCenterTextTypeface(MainActivity.typeFaceLight);

        pieChart.setHoleRadius(50f);
        pieChart.setDescription("");
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);
        pieChart.setTransparentCircleRadius(60f);


        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

        pieChart.setCenterText("Initiate\nCount");
        pieChart.setCenterTextSize(24f);
        pieChart.setCenterTextColor(ColorTemplate.getHoloBlue());

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        //data
        yVals1.add(new Entry((float) StatsFragment.analytics.getInitiateCount().getSent(), 0));
        yVals1.add(new Entry((float) StatsFragment.analytics.getInitiateCount().getReceived(), 1));


        //labels
        xVals.add("You");
        xVals.add("Them");

        //title
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(6f);

        // colors for stuff
        ArrayList<Integer> colors = new ArrayList<Integer>();

        //for (int c : ColorTemplate.VORDIPLOM_COLORS)
        //    colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
/*
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);


        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(MainActivity.typeFaceRegular);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();


        pieChart.animateXY(1500, 1500);
        pieChart.spin(2000, 0, 360);

       /* Legend l = pieChart.getLegend();
        l.setEnabled(false);
        l.setPosition(null);

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


}
