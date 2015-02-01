package com.radiance.chatstats;

import android.util.Log;

import java.util.ArrayList;
import com.radiance.chatstats.SMS.Status;

//Static Class
//Static Class
public class Analytics {// returns statistics to the fragments for display

    private static ArrayList<String> commonWords;
    private static ArrayList<String> emoticons;
    private static ConversationThread c;
    private static final String [] EMOTICONS = {":p", ":)", ";)",":S"};

    public Analytics (ConversationThread c){// basic constructor, accepts the conversationThread
        this.c = c;



    }
    public static ArrayList<StatPoint> getEmoticonCount (){// returns an ArrayList of Statpoints signifying the number of each emoticon used
        ArrayList<StatPoint> temp = new ArrayList<StatPoint> ();
        for (int i = 0; i< EMOTICONS.length; i++){
            temp.add(searchFor(EMOTICONS[i]));
        }
        return temp;
    }
    private static StatPoint searchFor (String s){// private method used to search the SMS messages for a passed string
        ArrayList<SMS> searchArray = c.getMessages();
        int S =0, R = 0;

        for (int i = 0; i < searchArray.size(); i++){


            if (searchArray.get(i).getStatus() == Status.SENT)// checks enum for type of message
                S+=searchArray.get(i).getNumOf(s);
            else
                R+=searchArray.get(i).getNumOf(s);

        }
        return (new StatPoint(S,R));
    }

    //\/\/\/\/\/\/\/\WHO'S WINNING/\/\/\/\/\/\/\/\/\/\/\/\/
    public static StatPoint sentAndReceived()// returns amount of sent messages vs received
    {// maybe responses instead?
        int S = c.getSent().size();
        int R = c.getReceived().size();
        return (new StatPoint(S, R));
    }

    public static StatPoint getAvgMessageLengthWords() {// returns the average message length
        double S = 0.0, R = 0.0;

        double sentSize = 0, receivedSize = 0;
        Response temp;
        ArrayList<Response> responses = c.getResponses();

        for (int i = 0; i < responses.size(); i++)// cycles through the responses
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
        R /= receivedSize;// maybe truncate it at some point? decimals mean nothing

        return (new StatPoint(S, R));
    }

    public static StatPoint getInitiateCount ()// returns the amount of initiations per user
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

    public static StatPoint getResponseTime ()// returns average response time
    {
        ArrayList<Response> temp;
        ArrayList<Conversation> conversations = c.getConversations();

        Status curr;

        double timeSent = 0.0, timeReceived = 0.0;
        double responsesSent = 0.0, responsesReceived = 0.0;

        for (int i = 0; i < conversations.size(); i++)
        {
            temp = conversations.get(i).getConversation();// returns arrayList of responses. i know, confusing af
            if (temp.get(0).getStatus() == Status.SENT)
                responsesSent++;
            else
                responsesReceived++;
            for (int j = 1; j < temp.size(); j++)
            {
                if(temp.get(j).getStatus() == Status.SENT){
                    timeSent+=(temp.get(j).getDateStart()-temp.get(j-1).getDateEnd());
                    responsesSent++;
                }

                else{
                    timeReceived+=(temp.get(j).getDateStart()-temp.get(j-1).getDateEnd());
                    responsesReceived++;
                }

            }
        }
        Log.v("Time", "" + timeSent + "    " + timeReceived);
        timeSent /= (responsesSent*60000);//returns it in minutes
        timeReceived /= (responsesReceived*60000);// responsesSent = sentSize, we're duplicating code here

        return new StatPoint(timeSent, timeReceived);
    }
}
