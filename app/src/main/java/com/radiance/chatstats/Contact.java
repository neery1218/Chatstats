package com.radiance.chatstats;

/**
 * Created by Neerajen on 11/02/2015.
 */
public class Contact {
    private String name, address;

    public Contact(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return name;
    }

}
