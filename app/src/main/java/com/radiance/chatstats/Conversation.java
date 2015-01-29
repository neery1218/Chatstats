package com.radiance.chatstats;

import java.util.ArrayList;
import com.radiance.chatstats.SMS.Status;

/**
 * Created by Richard on 2014-09-19.
 */
public class Conversation {
    private ArrayList<Response> conversation;
    private long dateStart, dateEnd;
    private int receivedCount, sentCount;

    public Conversation (ArrayList<Response> responses)
    {
        conversation = responses;
        dateStart = responses.get(0).getDateStart();
        dateEnd = responses.get(responses.size()-1).getDateEnd();
        receivedCount = sentCount = 0;
        for (int i = 0; i < responses.size(); i++){
            if (responses.get(i).getStatus() == Status.SENT)
                sentCount++;
            else
                receivedCount++;

        }
    }
    public StatPoint getResponseCount (){
        return (new StatPoint(sentCount,receivedCount));
    }
    public Status getInitiator ()
    {
        return conversation.get(0).getStatus();
    }

    public int wordLength ()
    {
        int length = 0;
        for (Response msg : conversation)
        {
            length += msg.getLength();
        }
        return length;
    }

    @Override
    public String toString()
    {
        String string = "";
        for (int i = 0; i < conversation.size(); i++)
        {
            string += conversation.get(i).getStatus() + ":";
            string += conversation.get(i).toString();
            string += "\n";
        }

        return string;
    }

    public ArrayList<Response> getConversation() {
        return conversation;
    }

    public long getDateStart() {
        return dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }
}
