package com.radiance.chatstats;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;


public class EmoticonsFragment extends Fragment implements FragmentLifeCycle {
    RadarChart radarChart;
    ArrayList<StatPoint> emoticons;

    public EmoticonsFragment() {
        // Required empty public constructor
    }

    public static EmoticonsFragment newInstance(String param1, String param2) {
        EmoticonsFragment fragment = new EmoticonsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emoticons = StatsFragment.analytics.getEmoticonCount();

    }

    public void setRadarChart() {
        //Title
        Log.d("TAG", emoticons.toString());
        radarChart.setDescription("");

        //Defines properties of the web
        radarChart.setWebLineWidth(2f);
        radarChart.setWebLineWidthInner(2f);
        radarChart.setWebAlpha(255);
        radarChart.setWebColor(Color.parseColor("#FFFFFF"));
        radarChart.setWebColorInner(Color.parseColor("#FFFFFF"));
        radarChart.setNoDataTextDescription("No emoticons! D:");

        int count = Analytics.EMOTICONS.length;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry((float) emoticons.get(i).getReceived(), i));
        }

        for (int i = 0; i < count; i++) {
            yVals2.add(new Entry((float) emoticons.get(i).getSent(), i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count; i++)
            xVals.add(Analytics.EMOTICONS[i]+"  ");

        RadarDataSet set1 = new RadarDataSet(yVals1, "Set 1");
        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);

        RadarDataSet set2 = new RadarDataSet(yVals2, "Set 2");
        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        set2.setDrawFilled(true);
        set2.setLineWidth(2f);

        ArrayList<RadarDataSet> sets = new ArrayList<RadarDataSet>();
        sets.add(set1);
        sets.add(set2);


        RadarData data = new RadarData(xVals, sets);
        data.setValueTypeface(MainActivity.oswaldLight);
        data.setValueTextSize(16f);
        data.setDrawValues(false);
        radarChart.setTouchEnabled(false);
        radarChart.setData(data);
        radarChart.getYAxis().setValueFormatter(
                new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return ""+(int)value;
                    }
                }
        );
        radarChart.getYAxis().setTextColor(Color.parseColor("#FFFFFF"));
        radarChart.invalidate();

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setTypeface(MainActivity.typeFaceRegular);
        xAxis.setTextSize(16f);
        xAxis.setTextColor(Color.parseColor("#FFFFFF"));

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setTypeface(MainActivity.oswaldLight);
        yAxis.setLabelCount(5);
        yAxis.setTextSize(16f);
        yAxis.setTextColor(Color.parseColor("#FFFFFF"));
        yAxis.setStartAtZero(true);

        radarChart.setRotationEnabled(false);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emoticons, container, false);

        TextView numberEmoticons = (TextView) view.findViewById(R.id.numberEmoticons);
        numberEmoticons.setTextColor(Color.WHITE);
        numberEmoticons.setTypeface(MainActivity.oswaldLight);
        numberEmoticons.setTextSize(48);

        radarChart = (RadarChart) view.findViewById(R.id.radarChart);
        setRadarChart();

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
        radarChart.animateXY(1000, 1000);
    }
}
