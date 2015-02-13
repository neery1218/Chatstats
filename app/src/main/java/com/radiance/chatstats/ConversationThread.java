package com.radiance.chatstats;

/**
 * Created by Neerajen on 28/01/2015.
 */

import android.database.Cursor;
import android.util.Log;

import com.radiance.chatstats.SMS.Status;

import java.util.ArrayList;
import java.util.Collections;

public class ConversationThread {//holds array of conversations
    //TODO retrieve method doesn't error check for null case...
    private static long threshold = 1000*3600;

    private ArrayList<SMS> messages;
    private ArrayList<SMS> sent;
    private ArrayList<SMS> received;
    private ArrayList<Response> responses;
    private ArrayList<Conversation> conversations;

    private String address;

    public ConversationThread(Cursor r, Cursor s, String address)//basic constructor
    {
        this.address = address;

        this.messages = new ArrayList<SMS>();
        this.sent = new ArrayList<SMS>();
        this.received = new ArrayList<SMS>();
        this.responses = new ArrayList<Response>();
        this.conversations = new ArrayList<Conversation>();

        this.received = retrieve(r, Status.RECEIVED);
        this.sent = retrieve(s, Status.SENT);

        messages.addAll(received);
        messages.addAll(sent);

        Collections.sort(messages);// sorts them in ascending chronological order
        initializeResponses();
        initializeConversations();
    }

    public void adjustToDate()
    {

    }

    public ArrayList<SMS> getMessages() {
        return messages;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<SMS> getSent() {
        return sent;
    }

    public ArrayList<SMS> getReceived() {
        return received;
    }

    public ArrayList<Response> getResponses() {
        return responses;
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    private void initializeResponses() {//sorts the SMS messages into blocks of responses
        int begin = 0, end = 0;
        Status key = messages.get(0).getStatus();
        Response temp;

        while (end != messages.size() - 1) {
            end = begin;
            while (end < (messages.size() - 1) && messages.get(end + 1).getStatus() == key && (messages.get(end+1).getDate() - messages.get(end).getDate() < threshold)) {
                end++;
            }

            temp = new Response(new ArrayList<SMS>(messages.subList(begin, end + 1)), key);
            responses.add(temp);

            if (end + 1 != messages.size()) {
                key = messages.get(end + 1).getStatus();
            }

            //Log.v("Flag", "(" + begin + ", " + end);
            begin = end + 1;
        }

    }

    private void initializeConversations()//arranges the responses into Conversations based on time interval between next response
    {
        int begin = 0, end = 0;
        Conversation temp;

        while (end != responses.size() - 1) {
            end = begin;
            while (end < (responses.size() - 1) && (responses.get(end+1).getDateStart() - responses.get(end).getDateEnd() < threshold)) {
                end++;
            }

            temp = new Conversation(new ArrayList<Response>(responses.subList(begin, end + 1)));
            conversations.add(temp);

            Log.v("Flag", "(" + begin + ", " + end + ")");
            //Log.v("Flag", temp.toString());
            begin = end + 1;
        }
    }

    private boolean matchAddress(String temp, String key)//checks if two addresses are the same
    {
        //Processes temp to get rid of brackets and stuff
        String tempProcessed = "";
        String keyProcessed = "";
        for (int i = 0; i < temp.length(); i++)//remove all non-digit characters ( ')',')','+','-')
        {
            if (Character.isDigit(temp.charAt(i)))
            {
                tempProcessed += temp.charAt(i);
            }
        }
        for (int i = 0; i < key.length(); i++)//same as above, but for keyProcessed
        {
            if (Character.isDigit(key.charAt(i)))
            {
                keyProcessed += key.charAt(i);
            }
        }

        if (tempProcessed.contains(keyProcessed) || keyProcessed.contains(tempProcessed) & tempProcessed.length() > 9 & keyProcessed.length() > 9)//works for general ten digit numbers, but also pushes in five digit numbers
            return true;

        return false;
    }

    private ArrayList<SMS> retrieve(Cursor c, Status status) { //retrieves all messages from the cursor via query
        ArrayList<SMS> messages = new ArrayList<SMS>();
        for (int i = 0; i < c.getCount(); i++)
        {
            c.moveToPosition(i);
            if (matchAddress(c.getString(c.getColumnIndexOrThrow("address")),address))
            {

                long date = c.getLong(c.getColumnIndexOrThrow("date"));
                String body = c.getString(c.getColumnIndexOrThrow("body"));
                messages.add(new SMS(date, body, status));
            }
        }
        return messages;
    }
}

