package com.radiance.chatstats;

/**
 * Created by Neerajen on 12/09/2014.
 */
public class StatPoint
{
    private double sent, received;

    public StatPoint (double sent, double received)
    {
        this.sent = sent;
        this.received = received;
    }

    @Override
    public String toString ()
    {
        return ("(SENT: " + sent + ", RECEIVED: " + received + ")");
    }

    public double getSent() {
        return sent;
    }

    public double getReceived() {
        return received;
    }
}