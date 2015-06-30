package com.radianceTOPS.chatstats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.radiance.chatstats.R;

import java.util.ArrayList;

/**
 * Created by Richard on 2015-02-14.
 */
public class PhoneNumberAdapter extends ArrayAdapter
{
    private Context context;
    private ArrayList<String> address;

    public PhoneNumberAdapter(Context context, ArrayList<String> address)
    {
        super(context, R.layout.phone_number,address);
        this.context = context;
        this.address = address;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.phone_number, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text2);

        textView.setText(address.get(position));
        return rowView;
    }
}
