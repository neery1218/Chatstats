package com.radiance.chatstats;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class LoadingFragment extends Fragment {

    private final Handler handler = new Handler();
    private Contact contact;
    private OnFragmentInteractionListener mListener;
    private int cursorsFinished;
    private ImageView logoImage;
    private ImageView logoName;
    private ArrayList<String> address;
    private ConversationThread conversationThread;
    private ArrayList<Conversation> messages;
    private Analytics analytics;

    public LoadingFragment() {
        // Required empty public constructor
    }

    public static LoadingFragment newInstance(String param1, String param2) {
        LoadingFragment fragment = new LoadingFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //start cursor query via asynctask,
        cursorsFinished = 0;

        if (getArguments() != null) {
            address = getArguments().getStringArrayList("phoneNumber");
            contact = new Contact(getArguments().getString("name"), address, getArguments().getInt("id"));
        }
        //you need to use two load cursors
        new Thread(new LoadCursor(address.get(0))).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        logoImage = (ImageView) view.findViewById((R.id.logoImage));
        logoName = (ImageView) view.findViewById((R.id.logoName));
        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLoadingFinished(ArrayList<StatPoint> bigThree);
    }

    private class LoadCursor implements Runnable {
        String address;

        LoadCursor(String address) {
            this.address = address;
        }

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    logoImage.setImageResource(R.drawable.logoimage);
                    logoName.setImageResource(R.drawable.logoname);
                    AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
                    anim.setDuration(1000);
                    anim.setRepeatCount(10);
                    anim.setRepeatMode(Animation.REVERSE);
                    logoImage.startAnimation(anim);
                }
            });

            //TODO switch to Telephony.SMS content provider
            //get sent messages
            Cursor sCursor = getActivity().getContentResolver().query(Uri.parse("content://sms/sent"), new String[]{"address", "body", "date"}, null, null, null);//initial query gets all contacts
            cursorsFinished++;
            //get received messages
            Cursor rCursor = getActivity().getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"address", "body", "date"}, null, null, null);//initial query gets all contacts
            cursorsFinished++;

            //get Analytics class
            conversationThread = new ConversationThread(rCursor, sCursor, address);

            /*handler.post(new Runnable() {
                @Override
                public void run() {
                    text.setText("running Analytics...");
                }
            });*/
            // messages = conversationThread.getConversations();
            analytics = new Analytics(conversationThread);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onLoadingFinished(analytics.getBigThree());
                }
            });
        }
    }

}
