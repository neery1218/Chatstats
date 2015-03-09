package com.radiance.chatstats;

import android.app.Activity;
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


public class ResponseTimeFragment extends Fragment {
    HorizontalBarChart barChart;
    StatPoint responseTime;
    TextView titleTextView;
    TextView sentNameTextView;
    TextView receivedNameTextView;
    TextView sentTimeTextView;
    TextView receivedTimeTextView;
    public ResponseTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResponseTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResponseTimeFragment newInstance(String param1, String param2) {
        ResponseTimeFragment fragment = new ResponseTimeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        responseTime = StatsFragment.getAnalytics().getResponseTime();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_response_time, container, false);
        titleTextView = (TextView) view.findViewById(R.id.titleView);
        titleTextView.setText("Average Response Time");
        titleTextView.setTextSize(25f);
        titleTextView.setTypeface(MainActivity.oswaldBold);
        barChart = (HorizontalBarChart) view.findViewById(R.id.barChart);

        sentTimeTextView = (TextView) view.findViewById(R.id.sentTime);
        receivedTimeTextView = (TextView) view.findViewById(R.id.receivedTime);
        sentNameTextView = (TextView) view.findViewById(R.id.sentName);
        receivedNameTextView = (TextView) view.findViewById(R.id.receivedName);

        receivedNameTextView.setText("Them: ");
        sentNameTextView.setText("You: ");

        sentTimeTextView.setText(((int) (responseTime.getSent() * 10) * 1.0) / 10 + " minutes");
        receivedTimeTextView.setText(((int) (responseTime.getReceived() * 10) * 1.0) / 10 + " minutes");

        receivedNameTextView.setTypeface(MainActivity.typeFaceRegular);
        sentNameTextView.setTypeface(MainActivity.typeFaceRegular);
        receivedTimeTextView.setTypeface(MainActivity.typeFaceBold);
        sentTimeTextView.setTypeface(MainActivity.typeFaceBold);

        receivedTimeTextView.setTextSize(30f);
        sentTimeTextView.setTextSize(30f);
        receivedNameTextView.setTextSize(30f);
        sentNameTextView.setTextSize(30f);


        setBarGraph();
        return view;
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
        // xl.setEnabled(false);

        YAxis yl = barChart.getAxisLeft();
        yl.setTypeface(MainActivity.typeFaceRegular);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
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

        yVals1.add(new BarEntry((float) responseTime.getSent(), 0));
        yVals2.add(new BarEntry((float) responseTime.getReceived(), 0));

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
