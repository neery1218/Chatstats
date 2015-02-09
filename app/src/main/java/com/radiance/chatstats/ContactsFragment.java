package com.radiance.chatstats;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.provider.ContactsContract;


import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ContactsFragment extends Fragment implements AbsListView.OnItemClickListener {


    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;//The fragment's ListView/GridView.
    private ListAdapter mAdapter;//The Adapter which will be used to populate the ListView/GridView
    private CursorAdapter cAdapter;
    ArrayList<String> numbers;


    // TODO: Rename and change types of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    public ContactsFragment() {//mandatory constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> test = getContacts();
        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, test);
    }

    public ArrayList<String> getContacts (){
        Cursor cCursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        ArrayList<String> test = new ArrayList<String> ();
         numbers = new ArrayList<String> ();
        if (cCursor.getCount() > 0){
            while (cCursor.moveToNext()){
                if (Integer.parseInt(cCursor.getString(cCursor.getColumnIndex( ContactsContract.Contacts.HAS_PHONE_NUMBER )))==1){
                    test.add(cCursor.getString(cCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));// get name
                    Cursor pCursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{cCursor.getString(cCursor.getColumnIndex(ContactsContract.Contacts._ID))}, null);
                    //get phone number
                   pCursor.moveToFirst();
                    numbers.add(pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    pCursor.close();



                }

            }
        }
        return test;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {//called when attached to fragment
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            Log.v("number",numbers.get(position));
            mListener.onFragmentInteraction(numbers.get(position));//return the address
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
