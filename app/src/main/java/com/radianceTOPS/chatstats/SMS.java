package com.radianceTOPS.chatstats;

import java.util.Date;

public class SMS implements Comparable<SMS>
{
    private long date;
    private String body;
    //Enum
    private Status status;

    public SMS (long date, String body, Status status)
    {
        this.date = date;
        this.body = body;
        this.body.trim();
        this.status = status;
    }

    public int compareTo(SMS other){// used when Collections.sort(messages) is called in conversationThread class
        return Long.compare(date, other.getDate());
    }
    //Returns Date Object
    public Date unixToDate ()
    {
        return (new Date (date));
    }

    //Gets message length in characters
    public int charLength()
    {
        return body.length();
    }

    //Gets message length in words
    public int wordLength()
    {
        String [] words = body.split("[ ]{1,}");
        return words.length;
    }

    public  int getNumOf (String s, boolean regex)
    {
        String [] words = body.split("[ ]{1,}");
        //Log.d("YOLO", Arrays.toString(words));
        int n = 0;
        if (!regex)
        {
            for (int i = 0; i < words.length; i++) {
                if (words[i].toUpperCase().equals(s.toUpperCase())) {
                    n++;
                }
            }
        }

        else
        {
            for (int i = 0; i < words.length; i++) {
                //Log.d("YOLO", words[i].matches(s)+"");
                if (words[i].matches(s)) {
                    n++;
                }
            }
        }
        return n;
    }
    //Getters
    public long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return body;
    }

    public Status getStatus(){
        return status;
    }

    //Enum for status
    public enum Status {
        SENT, RECEIVED
    }
}
