package com.radianceTOPS.chatstats;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.radiance.chatstats.R;

import java.util.ArrayList;

/**
 * Created by Neerajen on 11/02/2015.
 */
public class ContactAdapter extends ArrayAdapter {
    //private ArrayList<Color> backGround;
    //private ArrayList<Color> text;
    private final int[] textColor = {Color.parseColor("#FFFFFF")};
    private final int[] backgroundColor =
            {Color.parseColor("#E84C3D"),
                    Color.parseColor("#F1C40F"),
                    Color.parseColor("#1BBC9B"),
                    Color.parseColor("#3598DB"),
                    Color.parseColor("#297FB8"),
                    Color.parseColor("#16A086"),
                    Color.parseColor("#2DCC70"),
                    Color.parseColor("#34495E"),
                    Color.parseColor("#8D44AD")};
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
        textView.setTextColor(textColor[position % textColor.length]);
        textView.setBackgroundColor(backgroundColor[position % backgroundColor.length]);
        return rowView;
    }
}
