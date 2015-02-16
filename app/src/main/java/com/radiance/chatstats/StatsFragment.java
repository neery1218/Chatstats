package com.radiance.chatstats;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
public class StatsFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private final int RECEIVED = 1;
    private final int SENT = 0;
    TextView text;
    ArrayList<String> address;
    ConversationThread conversationThread;
    ArrayList<Conversation> messages;
    Analytics analytics;
    Contact contact;
    private Cursor rCursor, sCursor;
    private OnToBeDeterminedListener mListener;
    private int loadersFinished;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance() {
        StatsFragment fragment = new StatsFragment();
        return fragment;
    }

    public void displayMessage(View view) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadersFinished = 0;
        if (getArguments() != null) {
            address = getArguments().getStringArrayList("phoneNumber");
            contact = new Contact(getArguments().getString("name"), address, getArguments().getInt("id"));

        }
        //TODO make Analytics accept a null first, because the cursorLoader has not finished creating the ConversationThread object yet
        //calling all the cursors
        getLoaderManager().initLoader(RECEIVED, null, this);
        getLoaderManager().initLoader(SENT, null, this);

        // mCursor.moveToFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        //TODO an actual loading screen while the cursorLoaders finish
        text = (TextView) view.findViewById((R.id.textView));
        //text.setText(" " + analytics.getSentAndReceived());
        text.setText("testing");
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case SENT:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        Uri.parse("content://sms/sent"),        // Table to query
                        new String[]{"address", "body", "date"},     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            case RECEIVED:
                // Returns a new CursorLoader
                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        Uri.parse("content://sms/inbox"),        // Table to query
                        new String[]{"address", "body", "date"},     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );

            default:
                // An invalid id was passed in
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case RECEIVED:
                rCursor = data;
                loadersFinished++;
                break;
            case SENT:
                sCursor = data;
                loadersFinished++;
                break;
            default:
                break;

        }
        if (loadersFinished == 2) {
            conversationThread = new ConversationThread(rCursor, sCursor, address.get(0));
            messages = conversationThread.getConversations();
            analytics = new Analytics(conversationThread);
            text.setText("done!");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //TODO figure out if we need anything here, or if LoaderManager takes care of garbage collection for us
    }

    public interface OnToBeDeterminedListener {
        public void onToBeDetermined(ArrayList<String> id);
    }

}
