package com.radiance.chatstats;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.radiance.chatstats.SMS.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;

//Static Class
//Static Class
public class Analytics {
    public static String[] EMOTICONS = {":p", ":)", ";)", "<3", ":D", "</3"};
    //Dictionaries
    private static ArrayList<String> commonWords;
    //Variables
    private ConversationThread c;
    private ArrayList<StatPoint> emoticonCount;
    private StatPoint sentAndReceived;
    private StatPoint avgMessageLengthWords;
    private StatPoint initiateCount;
    private StatPoint responseTime;
    private ArrayList<DateFrequency> dateFrequencies;
    private StatPoint[] hourFrequencies;
    private MainActivity mainActivity;

    public Analytics(ConversationThread c, MainActivity mainActivity) {
        this.c = c;
        this.mainActivity = mainActivity;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        String[] checkboxEmoticons = sharedPref.getStringSet("emoticons_key", null).toArray(new String[0]);
        ArrayList<String> customEmoticonsSet = new ArrayList<String>(Arrays.asList(sharedPref.getString("custom_emoticons_key",null).split("\\s*,\\s*")));
        String [] customEmoticons;

        for (int i = 0; i < customEmoticonsSet.size(); i++)
        {
            customEmoticonsSet.set(i,customEmoticonsSet.get(i).trim());
        }
        customEmoticonsSet.remove("");
        customEmoticons = customEmoticonsSet.toArray(new String[0]);

        Log.d("TAG", Arrays.toString(checkboxEmoticons));
        Log.d("TAG", Arrays.toString(customEmoticons));

        EMOTICONS = new String[checkboxEmoticons.length + customEmoticons.length];
        System.arraycopy(checkboxEmoticons, 0, EMOTICONS, 0, checkboxEmoticons.length);
        System.arraycopy(customEmoticons, 0, EMOTICONS, checkboxEmoticons.length, customEmoticons.length);

        Log.d("TAG",Arrays.toString(EMOTICONS));

        emoticonCount = calcEmoticonCount();
        sentAndReceived = calcSentAndReceived();

        //the Big Three!
        avgMessageLengthWords = calcAvgMessageLengthWords();
        initiateCount = calcInitiateCount();
        responseTime = calcResponseTime();
        hourFrequencies = calcHourFrequencies();
    }

    public StatPoint[] getHourFrequencies() {
        return hourFrequencies;
    }
    public ArrayList<StatPoint> getBigThree() {//used by mainActivity to send to StatsFragment
        ArrayList<StatPoint> bigThree = new ArrayList<StatPoint>();
        bigThree.add(avgMessageLengthWords);
        bigThree.add(initiateCount);
        bigThree.add(responseTime);
        return bigThree;
    }

    public ArrayList<StatPoint> getEmoticonCount() {
        return emoticonCount;
    }

    public StatPoint getSentAndReceived() {
        return sentAndReceived;
    }

    public StatPoint getAvgMessageLengthWords() {
        return avgMessageLengthWords;
    }

    public StatPoint getInitiateCount() {
        return initiateCount;
    }

    public StatPoint getResponseTime() {
        return responseTime;
    }

    public int getTotalMessages() {
        return c.getNumMessages();
    }

    public int getTotalConversations() {
        return c.getNumConversations();
    }

    public ArrayList<StatPoint> searchFor(String[] str, Boolean regex) {
        ArrayList<StatPoint> temp = new ArrayList<StatPoint>();
        for (int i = 0; i < str.length; i++) {
            temp.add(searchFor(str[i], regex));
        }
        return temp;
    }

    public StatPoint searchFor(String s, Boolean regex) // initializes used to search the SMS messages for a passed string
    {

        ArrayList<SMS> searchArray = c.getMessages();
        int S = 0, R = 0;

        for (int i = 0; i < searchArray.size(); i++) {

            if (searchArray.get(i).getStatus() == Status.SENT)// checks enum for type of message
                S += searchArray.get(i).getNumOf(s, regex);
            else
                R += searchArray.get(i).getNumOf(s, regex);
        }

        return (new StatPoint(S, R));
    }

    public StatPoint calcSentAndReceived() // initializes amount of sent messages vs received
    {// maybe responses instead?
        int S = c.getSent().size();
        int R = c.getReceived().size();
        return (new StatPoint(S, R));
    }

    public StatPoint calcAvgMessageLengthWords() // initializes the average message length
    {
        double S = 0.0, R = 0.0;

        double sentSize = 0, receivedSize = 0;
        Response temp;
        ArrayList<Response> responses = c.getResponses();

        for (int i = 0; i < responses.size(); i++)// cycles through the responses
        {
            temp = responses.get(i);
            if (temp.getStatus() == Status.RECEIVED) {
                R += temp.getLength();
                receivedSize += 1;
            } else {
                S += temp.getLength();
                sentSize += 1;
            }
        }

        S /= sentSize;
        R /= receivedSize;// maybe truncate it at some point? decimals mean nothing

        return (new StatPoint(S, R));
    }

    private ArrayList<StatPoint> calcEmoticonCount() // initializes an ArrayList of Statpoints signifying the number of each emoticon used
    {
        return searchFor(EMOTICONS, false);
    }

    public StatPoint calcInitiateCount() // initializes the amount of initiations per user
    {
        ArrayList<Conversation> conversations = c.getConversations();

        int S = 0, R = 0;

        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).getInitiator() == Status.RECEIVED) {
                R++;
            } else {
                S++;
            }
        }

        return (new StatPoint(S, R));
    }

    public StatPoint calcResponseTime() // initializes average response time
    {
        ArrayList<Response> temp;
        ArrayList<Conversation> conversations = c.getConversations();

        Status curr;

        double timeSent = 0.0, timeReceived = 0.0;
        double responsesSent = 0.0, responsesReceived = 0.0;

        for (int i = 0; i < conversations.size(); i++) {
            temp = conversations.get(i).getConversation();// returns arrayList of responses. i know, confusing af
            if (temp.get(0).getStatus() == Status.SENT)
                responsesSent++;
            else
                responsesReceived++;
            for (int j = 1; j < temp.size(); j++) {
                if (temp.get(j).getStatus() == Status.SENT) {
                    timeSent += (temp.get(j).getDateStart() - temp.get(j - 1).getDateEnd());
                    responsesSent++;
                } else {
                    timeReceived += (temp.get(j).getDateStart() - temp.get(j - 1).getDateEnd());
                    responsesReceived++;
                }

            }
        }

        Log.v("Time", "" + timeSent + "    " + timeReceived);
        timeSent /= (responsesSent);//returns it in seconds right
        timeReceived /= (responsesReceived);// responsesSent = sentSize, we're duplicating code here

        return new StatPoint(timeSent, timeReceived);//uses statpoint object
    }

    private StatPoint[] calcHourFrequencies ()
    {
        ArrayList<SMS> messages = c.getMessages();
        Calendar helper = Calendar.getInstance();
        StatPoint[] temp = new StatPoint[24];
        for (int i = 0; i < temp.length; i++)
        {
            temp[i] = new StatPoint();
        }
        for (int i = 0; i < messages.size(); i++)
        {
            SMS current = messages.get(i);
            helper.setTimeInMillis(current.getDate());

            //Get parameters
            int hour = helper.get(Calendar.HOUR_OF_DAY);
            Status status = current.getStatus();

            //Log.d("TAG", "Hour: " + hour);

            if (status == Status.RECEIVED)
                temp[hour].incrementReceived();
            else
                temp[hour].incrementSent();
        }

        for (int i = 0; i < temp.length; i++)
        {
            //Log.d("freq", i + " o clock: " + temp[i]);
        }
        return temp;
    }

    public int getSize() {
        return c.getMessages().size();
    }
}