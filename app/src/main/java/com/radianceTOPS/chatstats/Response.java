package com.radianceTOPS.chatstats;

import java.util.ArrayList;

/**
 * Created by Neerajen on 12/09/2014.
 */
public class Response {
    private ArrayList<SMS> response;
    private SMS.Status status;
    private long dateStart, dateEnd;

    public Response (ArrayList<SMS> sms, SMS.Status status)
    {
        response = sms;
        this.status = status;

        dateStart = response.get(0).getDate();
        dateEnd = response.get(response.size()-1).getDate();
    }

    public int getLength()
    {
        int length = 0;
        for (SMS msg : response)
        {
            length += msg.wordLength();
        }

        return length;
    }

    public ArrayList<SMS> getResponse() {
        return response;
    }

    public long getDateStart() {
        return dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public SMS.Status getStatus (){
        return status;
    }

    @Override
    public String toString()
    {
        String string = "";

        for (int i = 0; i < response.size(); i ++)
        {
            string += "\n";
            string += response.get(i).toString();
        }

        return (string);
    }

    public int size ()
    {
        return (response.size());
    }

}
