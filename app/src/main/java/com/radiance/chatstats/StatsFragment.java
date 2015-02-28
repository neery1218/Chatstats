package com.radiance.chatstats;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    ArrayList<String> address;
    Contact contact;
    private String responseTime;
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

        if (getArguments() != null) {
            responseTime = getArguments().getString("responseTime");

        }


        // mCursor.moveToFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        text = (TextView) view.findViewById((R.id.textView));
        text.setText(responseTime);
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
