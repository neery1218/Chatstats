package com.radiance.chatstats;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;


public class MessageLengthFragment extends Fragment {
    BarChart barChart;


    public MessageLengthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageLengthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageLengthFragment newInstance(String param1, String param2) {
        MessageLengthFragment fragment = new MessageLengthFragment();

        return fragment;
    }

    public void setBarGraph() {
        barChart.setDrawBarShadow(true);
        barChart.setDrawValueAboveBar(true);

        barChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        barChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false)
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(MainActivity.typeFaceRegular);
        xAxis.setDrawGridLines(false);

        // ValueFormatter custom = new MyValueFormatter();

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTypeface(MainActivity.typeFaceRegular);
        leftAxis.setLabelCount(8);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            xVals.add("a" + i);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < 5; i++) {

            yVals1.add(new BarEntry((float) i, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
//        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(10f);
        data.setValueTypeface(MainActivity.typeFaceRegular);

        barChart.setData(data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_length, container, false);
        barChart = (BarChart) view.findViewById(R.id.barChart);
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
