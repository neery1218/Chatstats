package com.radiance.chatstats;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.radiance.chatstats.StatsFragment.OnToBeDeterminedListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment implements View.OnClickListener {

    private static Analytics analytics;
    TextView text;

    PieChart pieChart;
    ArrayList<String> address;
    Contact contact;
    StatPoint responseTime;
    StatPoint initiateCount;
    StatPoint avgMessageLength;
    private OnToBeDeterminedListener mListener;


    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance() {
        StatsFragment fragment = new StatsFragment();
        return fragment;
    }

    public static void setAnalytics (Analytics analytic){
        analytics = analytic;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* if (getArguments() != null) {

            bigThree = getArguments().getStringArrayList(MainActivity.ARG_BIG_THREE);

        }*/


        // mCursor.moveToFirst();
    }

    public void setPieGraph() {//for initiate Count

        pieChart.setUsePercentValues(true);

        // change the color of the center-hole
        pieChart.setHoleColor(Color.rgb(255, 255, 255));
        //pieChart.setHoleColorTransparent(true);

        // tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // pieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        pieChart.setHoleRadius(60f);
        pieChart.setDescription("");
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);
        pieChart.setTransparentCircleRadius(90f);


        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //pieChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

        pieChart.setCenterText("Initiate\nCount");


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        //data
        yVals1.add(new Entry((float) analytics.getInitiateCount().getSent(), 0));
        yVals1.add(new Entry((float) analytics.getInitiateCount().getReceived(), 1));

        //labels
        xVals.add("You");
        xVals.add("Them");

        //title
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);

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
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        // data.setValueTypeface(tf);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();


        pieChart.animateXY(1500, 1500);
        pieChart.spin(2000, 0, 360);

        //Legend l = pieChart.getLegend();
        // l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        // l.setXEntrySpace(7f);
        // l.setYEntrySpace(5f);
    }

    public void setLineGraph() {
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();
        ArrayList<Entry> valsComp2 = new ArrayList<Entry>();
        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        // and so on ...

        Entry c2e1 = new Entry(120.000f, 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        Entry c2e2 = new Entry(110.000f, 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        //...

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        LineDataSet setComp2 = new LineDataSet(valsComp2, "Company 2");
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("2.Q");
        xVals.add("3.Q");
        xVals.add("4.Q");

        LineData data = new LineData(xVals, dataSets);
        //chart.setData(data);
        // chart.invalidate(); // refresh
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        text = (TextView) view.findViewById((R.id.textView));
        text.setText(bigThree.toString());
        Button button = (Button) view.findViewById((R.id.button));
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        text.setText("Yolo");
        //mCursor.moveToNext();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnToBeDeterminedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnContactSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnToBeDeterminedListener {
        public void onToBeDetermined(ArrayList<String> id);
    }

}
