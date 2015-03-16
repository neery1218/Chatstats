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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;

public class HourFrequencyFragment extends Fragment implements FragmentLifeCycle, OnChartValueSelectedListener {
    private TextView titleTextView;
    private TextView legendTextView;
    private TextView[] valueTextViews;
    private TextView[] nameTextViews;
    private BarChart barChart;
    private PieChart pieChart;
    private StatPoint[] hourFrequencies;
    private TextView mostFrequentTextView;
    private int[] frequency;
    private int max;
    private String[] timesOfDay = {"Morning", "Afternoon", "Evening", "Night"};
    private int[] colorChoices = {ColorTemplate.VORDIPLOM_COLORS[0], ColorTemplate.VORDIPLOM_COLORS[1], ColorTemplate.VORDIPLOM_COLORS[2], ColorTemplate.getHoloBlue()};

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
        frequency = new int[4];
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

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < times.length; i++) {
            colors.add(colorChoices[times[(i + 18) % 24]]);
        }
        dataSets.get(0).setColors(colors);
        max = 0;
        int min = 0;
        for (int i = 0; i < frequency.length; i++) {
            if (frequency[i] > frequency[max])
                max = i;
            if (frequency[i] < frequency[min])
                min = i;
        }


        // mostFrequentTextView.setText(""+ timesOfDay[max]);
        //  Spanned text = Html.fromHtml("Most Contacted Time\n<b>" + timesOfDay[max] + "</b>" + "\nLeast ContactedTime\n<b>" + timesOfDay[min] + "</b>");
        mostFrequentTextView.setText(timesOfDay[max]);
        mostFrequentTextView.setTextColor(colorChoices[max]);

       /* for (int i = 0; i < valueTextViews.length; i++) {
            valueTextViews[i].setText("" + frequency[i]);
            valueTextViews[i].setTextColor(colorChoices[i]);
            nameTextViews[i].setTextColor(colorChoices[i]);
        }*/


        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(0f);

        // data.setValueTypeface(MainActivity.typeFaceRegular);
        barChart.setData(data);
        barChart.getLegend().setEnabled(false);
    }

    public void setPieGraph() {//for initiate Count

        //  pieChart.setUsePercentValues(true);
        //  pieChart.setVisibility(View.INVISIBLE);
        // change the color of the center-hole
        pieChart.setHoleColor(Color.rgb(255, 255, 255));
        pieChart.setHoleColorTransparent(true);
        pieChart.setClickable(false);
        pieChart.setHoleRadius(50f);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);
        pieChart.setTransparentCircleRadius(40f);
        // pieChart.setTouchEnabled(false);
        pieChart.setRotationEnabled(false);

        pieChart.setCenterText("");
        pieChart.setCenterTextTypeface(MainActivity.oswaldLight);
        pieChart.setCenterTextSize(24f);
        pieChart.setCenterTextColor(Color.WHITE);

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
       /* double sent = StatsFragment.analytics.getInitiateCount().getSent();
        double received = StatsFragment.analytics.getInitiateCount().getReceived();
        *///data
        for (int i = 0; i < frequency.length; i++) {
            yVals1.add(new Entry((float) frequency[i], i));
            xVals.add("");
        }

        //text.setHeight();
        //text.setHeight()

        //labels

        //xVals.add("");

        //title
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(0f);

        dataSet.setColors(colorChoices);


        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(0f);


        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(MainActivity.typeFaceRegular);
        pieChart.setData(data);
        pieChart.setHighlightEnabled(true);
        pieChart.highlightTouch(new Highlight(max, 0));
        //pieChart.chart
        // undo all highlights
        // pieChart.highlightValues(null);

        pieChart.invalidate();


        //pieChart.spin(2000, 0, 360);
        pieChart.setDescription("");
        pieChart.setEnabled(false);
        Legend l = pieChart.getLegend();
        l.setEnabled(false);
        /*l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);*/
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
        titleTextView.setText("Most Active Time of Day");
        titleTextView.setTextSize(36f);
        titleTextView.setTypeface(MainActivity.oswaldLight);
      /*  titleTextView.setTextSize(26f);
        titleTextView.setTypeface(MainActivity.typeFaceRegular);*/
        titleTextView.setTextColor(Color.WHITE);


        mostFrequentTextView = (TextView) view.findViewById(R.id.mostFrequentTextView);
        mostFrequentTextView.setTextColor(Color.WHITE);
        mostFrequentTextView.setTypeface(MainActivity.typeFaceRegular);
        mostFrequentTextView.setTextSize(24f);
        // legendTextView = (TextView)view.findViewById(R.id.HourFrequencyLegend);
      /*  nameTextViews[0] = (TextView) view.findViewById(R.id.Title1);
        nameTextViews[1] = (TextView) view.findViewById(R.id.Title2);
        nameTextViews[2] = (TextView) view.findViewById(R.id.Title3);
        nameTextViews[3] = (TextView) view.findViewById(R.id.Title4);*/

     /*   nameTextViews[0].setText("Morning: ");
        nameTextViews[1].setText("Afternoon: ");
        nameTextViews[2].setText("Evening: ");
        nameTextViews[3].setText("Night: ");

        valueTextViews[0] = (TextView) view.findViewById(R.id.Value1);
        valueTextViews[1] = (TextView) view.findViewById(R.id.Value2);
        valueTextViews[2] = (TextView) view.findViewById(R.id.Value3);
        valueTextViews[3] = (TextView) view.findViewById(R.id.Value4);*/


        barChart = (BarChart) view.findViewById(R.id.hourFrequencyBarChart);
        pieChart = (PieChart) view.findViewById(R.id.hourFrequencyPieChart);
        pieChart.setOnChartValueSelectedListener(this);
        setBarGraph();
        setPieGraph();
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

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        pieChart.highlightValues(null);
        mostFrequentTextView.setText(timesOfDay[e.getXIndex()]);

        mostFrequentTextView.setTextColor(colorChoices[e.getXIndex()]);
        pieChart.setCenterText("" + (int) (frequency[e.getXIndex()] * 1000.0 / (frequency[0] + frequency[1] + frequency[2] + frequency[3])) * 1.0 / 10 + "%");

    }

    @Override
    public void onNothingSelected() {

    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
