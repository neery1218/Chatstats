package com.radiance.chatstats;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactPhoneNumberFragment extends Fragment implements ListView.OnItemClickListener {

    private OnPhoneNumberSelectedListener mListener;
    private ListView listView;
    private ArrayList<String> address;
    private Contact contact;
    private PhoneNumberAdapter phoneNumberAdapter;

    public ContactPhoneNumberFragment() {

    }

    public static ContactPhoneNumberFragment newInstance() {
        ContactPhoneNumberFragment fragment = new ContactPhoneNumberFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        address = getArguments().getStringArrayList("phoneNumber");

        contact = new Contact(getArguments().getString("name"), address, getArguments().getInt("id"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_phone_number, container, false);
        listView = (ListView) view.findViewById(R.id.phone_numbers_list);
        phoneNumberAdapter = new PhoneNumberAdapter(getActivity(), address);
        listView.setAdapter(phoneNumberAdapter);
        listView.setOnItemClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPhoneNumberSelectedListener) activity;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TAG", address.get(position));
        String temp = address.get(position);
        address = new ArrayList<String>();
        address.add(temp);
        contact.setAddress(address);
        mListener.onPhoneNumberSelected(contact);
    }

    public interface OnPhoneNumberSelectedListener {
        public void onPhoneNumberSelected(Contact contact);
    }
}