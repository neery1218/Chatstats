package com.radiance.chatstats;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;

public class HourFrequencyFragment extends Fragment implements FragmentLifeCycle {
    private TextView titleTextView;
    private TextView legendTextView;
    private TextView[] valueTextViews;
    private TextView[] nameTextViews;
    private BarChart barChart;
    private StatPoint[] hourFrequencies;

    public HourFrequencyFragment() {
        // Required empty public constructor
    }

    public static HourFrequencyFragment newInstance() {
        HourFrequencyFragment fragment = new HourFrequencyFragment();
        return fragment;
    }

    public void setBarGraph() {
        final int NIGHT = 3;
        final int MORNING = 0;
        final int AFTERNOON = 1;
        final int EVENING = 2;
        int[] frequency = new int[4];
        int times[] = {MORNING, MORNING, MORNING, MORNING, MORNING, MORNING, AFTERNOON, AFTERNOON, AFTERNOON, AFTERNOON, AFTERNOON, AFTERNOON, EVENING, EVENING, EVENING, NIGHT, NIGHT, NIGHT, NIGHT, NIGHT, NIGHT, NIGHT, NIGHT, NIGHT};
        barChart.setTouchEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);

        // barChart.setDrawBarShadow(true);


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
        xl.setTextColor(Color.WHITE);
        xl.setDrawLabels(true);
        // xl.setEnabled(false);

        YAxis yl = barChart.getAxisLeft();
        yl.setTypeface(MainActivity.typeFaceRegular);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisLineColor(Color.WHITE);
        yl.setAxisLineWidth(2f);
        yl.setTextColor(Color.WHITE);


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


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < hourFrequencies.length; i++) {
            yVals1.add(new BarEntry((float) (hourFrequencies[i].getTotal()), (i + 18) % 24));
            frequency[times[(i + 18) % 24]] += hourFrequencies[i].getTotal();
            if (i % 6 == 0) {
                int time = (i + 6) % 24;
                xVals.add("" + time + ":00");
            } else
                xVals.add("");

        }


        BarDataSet set1 = new BarDataSet(yVals1, "");

        // set1.setBarSpacePercent(1f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        int[] colorChoices = {ColorTemplate.VORDIPLOM_COLORS[0], ColorTemplate.VORDIPLOM_COLORS[1], ColorTemplate.VORDIPLOM_COLORS[2], ColorTemplate.getHoloBlue()};
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < times.length; i++) {
            colors.add(colorChoices[times[(i + 18) % 24]]);
        }
        dataSets.get(0).setColors(colors);

        for (int i = 0; i < valueTextViews.length; i++) {
            valueTextViews[i].setText("" + frequency[i]);
            valueTextViews[i].setTextColor(colorChoices[i]);
        }


        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(0f);

        // data.setValueTypeface(MainActivity.typeFaceRegular);
        barChart.setData(data);
        barChart.getLegend().setEnabled(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hourFrequencies = StatsFragment.getAnalytics().getHourFrequencies();
        nameTextViews = new TextView[4];
        valueTextViews = new TextView[4];



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hour_frequency, container, false);
        titleTextView = (TextView) view.findViewById(R.id.hourFrequencyTitleView);
        titleTextView.setText("Message Frequency");
        titleTextView.setTextSize(36f);
        titleTextView.setTypeface(MainActivity.oswaldLight);
        titleTextView.setTextColor(Color.WHITE);

        // legendTextView = (TextView)view.findViewById(R.id.HourFrequencyLegend);
        nameTextViews[0] = (TextView) view.findViewById(R.id.Title1);
        nameTextViews[1] = (TextView) view.findViewById(R.id.Title2);
        nameTextViews[2] = (TextView) view.findViewById(R.id.Title3);
        nameTextViews[3] = (TextView) view.findViewById(R.id.Title4);

        nameTextViews[0].setText("Morning: ");
        nameTextViews[1].setText("Afternoon: ");
        nameTextViews[2].setText("Evening: ");
        nameTextViews[3].setText("Night: ");

        valueTextViews[0] = (TextView) view.findViewById(R.id.Value1);
        valueTextViews[1] = (TextView) view.findViewById(R.id.Value2);
        valueTextViews[2] = (TextView) view.findViewById(R.id.Value3);
        valueTextViews[3] = (TextView) view.findViewById(R.id.Value4);


        barChart = (BarChart) view.findViewById(R.id.hourFrequencyBarChart);
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

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
        barChart.animateXY(0, 1000);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
