package com.radiance.chatstats;

import java.util.Date;

public class SMS implements Comparable<SMS>
{
    private long date;
    private String body;
    //Enum
    private Status status;
/*      
        public static void main (String [] args)
        {
                
        }*/

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

    public  int getNumOf (String s){
        String [] words = body.split("[ ]{1,}");
        int n = 0;
        for (int i = 0; i < words.length; i++){
            if (words[i].toUpperCase().equals(s.toUpperCase())){
                n++;
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
