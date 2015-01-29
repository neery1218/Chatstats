package com.radiance.chatstats;

import java.util.ArrayList;

import com.radiance.chatstats.SMS.Status;
import com.radiance.chatstats.SMS;

/**
 * Created by Neerajen on 12/09/2014.
 */
public class Response {
    private ArrayList<SMS> response;
    private Status status;
    private long dateStart, dateEnd;

    public Response (ArrayList<SMS> sms, Status status)
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

    public Status getStatus (){
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
