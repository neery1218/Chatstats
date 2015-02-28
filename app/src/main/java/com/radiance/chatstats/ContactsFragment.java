package com.radiance.chatstats;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsFragment extends Fragment implements AbsListView.OnItemClickListener {//displays a list of contacts for running statistics


    private OnContactSelectedListener mListener;
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
        contactAdapter = new ContactAdapter(getActivity(), contacts);


    }

    public ArrayList<Contact> getContacts() {//queries the contacts from a content provider
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = '" + ("1") + "'";
        String sortOrder = ContactsContract.Contacts.TIMES_CONTACTED + " COLLATE LOCALIZED DESC";
        Uri contactsURI = ContactsContract.Contacts.CONTENT_URI;
        Cursor cCursor = getActivity().getContentResolver().query(contactsURI, null, selection, null, sortOrder);//initial query gets all contacts
        String displayName = "";
        int id = 0;


        ArrayList<Contact> contacts = new ArrayList<Contact>();
        //some contacts don't have phone numbers, so they must be taken out
        if (cCursor.getCount() > 0){
            while (cCursor.moveToNext()){

                displayName = (cCursor.getString(cCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));// get name
                id = (cCursor.getInt(cCursor.getColumnIndex(ContactsContract.Contacts._ID)));// get id
                contacts.add(new Contact(displayName, id));

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
        // contacts = getContacts();//gets contacts from a cursor

        return view;
    }

    @Override
    public void onAttach(Activity activity) {//called when attached to fragment
        super.onAttach(activity);
        try {
            mListener = (OnContactSelectedListener) activity;
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

    public ArrayList<String> getAddress(Contact contact) {
        String tempNumber;
        ArrayList<String> number = new ArrayList<String>();
        //Queries a list of phone numbers
        Cursor pCursor = getActivity().getContentResolver().query
                (ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{Integer.toString(contact.getID())}, null);

        pCursor.moveToFirst();

        //Stores all the numbers of a contact
        while (!pCursor.isAfterLast()) {
            tempNumber = (pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            number.add(tempNumber);
            pCursor.moveToNext();
        }
        pCursor.close();//finalise cursor
        return number;

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//called when a number is selected
        if (null != mListener) {
            Contact contact = contacts.get(position);
            contact.setAddress(getAddress(contact));
            //Queries a list of phone numbers
            mListener.onContactSelected(contact);//returns address to MainActivity
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    public interface OnContactSelectedListener {

        public void onContactSelected(Contact contact);

    }

}
