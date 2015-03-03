package com.radiance.chatstats;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TotalMessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TotalMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotalMessagesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TotalMessagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TotalMessagesFragment newInstance(String param1, String param2) {
        TotalMessagesFragment fragment = new TotalMessagesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public TotalMessagesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_total_messages, container, false);
        TextView text = (TextView)view.findViewById(R.id.initialMessages);
        text.setText("Hi");
        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
