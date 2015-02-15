package com.radiance.chatstats;

import java.util.ArrayList;

/**
 * Created by Neerajen on 11/02/2015.
 */
public class Contact {
    private String name;
    private ArrayList<String> address;
    private int ID;

    public Contact(String name, ArrayList<String> address) {
        this.name = name;
        this.address = address;
    }

    public Contact(String name, int ID) {
        this.name = name;
        this.ID = ID;
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

    public void setADDress(ArrayList<String> address) {
        this.address = address;
    }
}
