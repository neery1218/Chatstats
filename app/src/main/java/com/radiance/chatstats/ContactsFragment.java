package com.radiance.chatstats;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class ContactsFragment extends Fragment implements AbsListView.OnItemClickListener {//displays a list of contacts for running statistics


    private OnFragmentInteractionListener mListener;
    private AbsListView mListView;//The fragment's ListView/GridView.
    private ListAdapter mAdapter;//The Adapter which will be used to populate the ListView/GridView
    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contacts;

    public ContactsFragment() {//mandatory constructor
    }

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = getContacts();//gets contacts from a cursor
        /*
        mAdapter = new ArrayAdapter<Contact>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, contacts);*/
        contactAdapter = new ContactAdapter(getActivity(), contacts);

    }

    public ArrayList<Contact> getContacts() {//queries the contacts from a content provider

        Cursor cCursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);//initial query gets all contacts
        String test = "";
        String tempNumber;
        ArrayList<String> number = new ArrayList<String>();


        ArrayList<Contact> contacts = new ArrayList<Contact>();
        //some contacts don't have phone numbers, so they must be taken out
        if (cCursor.getCount() > 0){
            while (cCursor.moveToNext()){
                number.clear();
                if (Integer.parseInt(cCursor.getString(cCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) == 1) //if the contact has a phone number, it is added to contacts
                {

                    test = (cCursor.getString(cCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));// get name

                    //Queries a list of phone numbers for each contact
                    Cursor pCursor = getActivity().getContentResolver().query
                            (ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{cCursor.getString(cCursor.getColumnIndex(ContactsContract.Contacts._ID))}, null);

                    pCursor.moveToFirst();

                    //Stores all the numbers of a contact
                    while(!pCursor.isAfterLast()) {
                        tempNumber = (pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        number.add(tempNumber);
                        pCursor.moveToNext();
                    }

                    //Clones to avoid referencing the same object
                    Contact tempContact = new Contact(test,(ArrayList<String>)number.clone());
                    contacts.add(tempContact);

                    pCursor.close();//finalise cursor
                }
            }
        }
        return contacts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(contactAdapter);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//called when a number is selected
        if (null != mListener) {
            Log.d("TAG", contacts.get(position).getName());
            Log.d("TAG", contacts.get(position).getAddress().toString());
            mListener.onFragmentInteraction(contacts.get(position).getAddress());//returns address to MainActivity
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }


    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(ArrayList<String> address);
    }

}
