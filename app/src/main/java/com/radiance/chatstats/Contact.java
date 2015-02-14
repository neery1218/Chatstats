package com.radiance.chatstats;

import java.util.ArrayList;

/**
 * Created by Neerajen on 11/02/2015.
 */
public class Contact {
    private String name;
    private ArrayList<String> address;

    public Contact(String name, ArrayList<String> address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return name + ": " + address.toString();
    }

}
