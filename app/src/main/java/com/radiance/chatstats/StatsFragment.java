package com.radiance.chatstats;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment implements View.OnClickListener {

    private Cursor rCursor, sCursor;
    TextView text;
    ArrayList<String> address;
    ConversationThread conversationThread;
    ArrayList<Conversation> messages;
    Analytics analytics;

    private OnFragmentInteractionListener mListener;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        return fragment;
    }

    public void displayMessage(View view) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            address = getArguments().getStringArrayList("address");

        }
        //calling all the cursors
        rCursor = getActivity().getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"address", "body", "date"}, null, null, null);
        sCursor = getActivity().getContentResolver().query(Uri.parse("content://sms/sent"), new String[]{"address", "body", "date"}, null, null, null);
        conversationThread = new ConversationThread(rCursor, sCursor, address.get(0));
        messages = conversationThread.getConversations();
        analytics = new Analytics(conversationThread);

        // mCursor.moveToFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        text = (TextView)view.findViewById((R.id.textView));
        text.setText(" "+ analytics.getSentAndReceived());
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
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(ArrayList<String> id);
    }

}
