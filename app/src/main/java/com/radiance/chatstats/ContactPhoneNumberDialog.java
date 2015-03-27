package com.radiance.chatstats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ContactPhoneNumberDialog extends DialogFragment implements ListView.OnItemClickListener {

    private OnPhoneNumberSelectedListener mListener;
    private ListView listView;
    private ArrayList<String> address;
    private Contact contact;
    private PhoneNumberAdapter phoneNumberAdapter;

    public ContactPhoneNumberDialog() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        address = getArguments().getStringArrayList("phoneNumber");
        contact = new Contact(getArguments().getString("name"), address, getArguments().getInt("id"));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.phone_number_dialog);
        builder.setItems(address.toArray(new String[address.size()]), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mListener.onPhoneNumberSelected(contact, which);
            }
        });
        return builder.create();
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
    }

    public interface OnPhoneNumberSelectedListener {
        public void onPhoneNumberSelected(Contact contact, int which);
    }
}