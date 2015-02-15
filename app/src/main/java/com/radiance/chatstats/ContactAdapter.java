package com.radiance.chatstats;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Neerajen on 11/02/2015.
 */
public class ContactAdapter extends ArrayAdapter {
    //private ArrayList<Color> backGround;
    //private ArrayList<Color> text;
    private final int[] textColor = {Color.parseColor("#50d07d")};
    private final int[] backgroundColor = {Color.parseColor("#B2CECF")};
    private Context context;
    private ArrayList<Contact> contacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, R.layout.list_item, contacts);
        this.contacts = contacts;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text1);

        textView.setText(contacts.get(position).getName());
        position %= textColor.length;
        textView.setTextColor(textColor[position]);
        textView.setBackgroundColor(backgroundColor[position]);
        return rowView;
    }
}
