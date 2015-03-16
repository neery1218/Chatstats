package com.radiance.chatstats;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.radiance.chatstats.StatsFragment.OnToBeDeterminedListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {

    public static Analytics analytics;
    private static String name;
    private static FrameLayout layout;
    private MyPagerAdapter statsPagerAdapter;
    private ViewPager viewPager;
    private LinePageIndicator titlePageIndicator;
    private OnToBeDeterminedListener mListener;


    public StatsFragment() {
        // Required empty public constructor
    }

    public static void setLayoutColor(String color) {
        layout.setBackgroundColor(Color.parseColor(color));
        Log.v("color", color);
    }

    public static StatsFragment newInstance() {
        StatsFragment fragment = new StatsFragment();
        return fragment;
    }

    public static Analytics getAnalytics() {
        return analytics;
    }

    public static void setAnalytics(Analytics analytic) {
        analytics = analytic;
    }

    public static String getName() {
        return name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            name = getArguments().getString(MainActivity.ARG_NAME);

        }

        // mCursor.moveToFirst();

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
        /*pieChart = (PieChart)view.findViewById(R.id.chart);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int height = point.y;
        pieChart.setMinimumHeight(height);
        setPieGraph();*/
        layout = (FrameLayout) view.findViewById(R.id.layout);
        //statsPagerAdapter = new StatsPagerAdapter(getActivity().getSupportFragmentManager());
        statsPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) view.findViewById((R.id.pager));


        viewPager.setAdapter(statsPagerAdapter);


        titlePageIndicator = (LinePageIndicator) view.findViewById(R.id.titles);
        titlePageIndicator.setViewPager(viewPager);
        titlePageIndicator.setOnPageChangeListener(pageChangeListener);


        final float density = getResources().getDisplayMetrics().density;
        titlePageIndicator.setSelectedColor(0x88FF0000);
        titlePageIndicator.setUnselectedColor(0xFF888888);
        titlePageIndicator.setStrokeWidth(4 * density);
        titlePageIndicator.setLineWidth(30 * density);
        setLayoutColor("#1BBC9B");
        return view;
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

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        int currentPosition = 0;
        String[] colors = {"#ff1bbc9b", "#ffaaaaaa", "#ffD1A551", "#ff34495e", "#ff000000"};
        @Override
        public void onPageSelected(int newPosition) {

            FragmentLifeCycle fragmentToHide = (FragmentLifeCycle) statsPagerAdapter.getItem(currentPosition);
            fragmentToHide.onPauseFragment();

            FragmentLifeCycle fragmentToShow = (FragmentLifeCycle) statsPagerAdapter.getItem(newPosition);
            fragmentToShow.onResumeFragment();
            setLayoutColor(colors[newPosition]);
            currentPosition = newPosition;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };


}
