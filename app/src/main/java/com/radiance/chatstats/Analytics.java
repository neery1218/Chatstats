package com.radiance.chatstats;

import java.util.ArrayList;
import com.radiance.chatstats.SMS.Status;

//Static Class
public class Analytics {
    private static ArrayList<String> commonWords;
    private static ArrayList<String> emoticons;
    private static ConversationThread c;
    private static final String [] EMOTICONS = {":p", ":)", ";)",":S"};

    public Analytics (ConversationThread c){
        this.c = c;



    }
    public static ArrayList<StatPoint> getEmoticonCount (){
        ArrayList<StatPoint> temp = new ArrayList<StatPoint> ();
        for (int i = 0; i< EMOTICONS.length; i++){
            temp.add(searchFor(EMOTICONS[i]));
        }
        return temp;
    }
    public static StatPoint searchFor (String s){
        ArrayList<SMS> searchArray = c.getMessages();
        int S =0, R = 0;

        for (int i = 0; i < searchArray.size(); i++){


            if (searchArray.get(i).getStatus() == Status.SENT)
                S+=searchArray.get(i).getNumOf(s);
            else
                R+=searchArray.get(i).getNumOf(s);

        }
        return (new StatPoint(S,R));
    }

    //\/\/\/\/\/\/\/\WHO'S WINNING/\/\/\/\/\/\/\/\/\/\/\/\/
    public static StatPoint sentAndReceived()
    {
        int S = c.getSent().size();
        int R = c.getReceived().size();
        return (new StatPoint(S, R));
    }

    public static StatPoint getAvgMessageLengthWords() {
        double S = 0.0, R = 0.0;

        double sentSize = 0, receivedSize = 0;
        Response temp;
        ArrayList<Response> responses = c.getResponses();

        for (int i = 0; i < responses.size(); i++)
        {
            temp = responses.get(i);
            if (temp.getStatus() == Status.RECEIVED)
            {
                R += temp.getLength();
                receivedSize += 1;
            }

            else
            {
                S += temp.getLength();
                sentSize += 1;
            }
        }

        S /= sentSize;
        R /= receivedSize;

        return (new StatPoint(S, R));
    }

    public static StatPoint getInitiateCount ()
    {
        ArrayList<Conversation> conversations = c.getConversations();

        int S = 0, R = 0;

        for (int i = 0; i < conversations.size(); i++)
        {
            if (conversations.get(i).getInitiator() == Status.RECEIVED) {R++;}

            else {S++;}
        }

        return (new StatPoint(S,R));
    }

    public static StatPoint getResponseTime ()
    {
        ArrayList<Response> temp;
        ArrayList<Conversation> conversations = c.getConversations();

        Status curr;

        double timeSent = 0.0, timeReceived = 0.0;
        double responsesSent = 0.0, responsesReceived = 0.0;

        for (int i = 0; i < conversations.size(); i++)
        {
            temp = conversations.get(i).getConversation();
            for (int j = 0; j < temp.size()-1; j++)
            {

            }
        }

        timeSent /= (responsesSent*60000);
        timeReceived /= (responsesReceived*60000);

        return new StatPoint(timeSent, timeReceived);
    }
}
