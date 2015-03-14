package com.radiance.chatstats;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Neerajen on 12/09/2014.
 */
public class StatPoint implements Parcelable {
    public static final Parcelable.Creator<StatPoint> CREATOR
            = new Parcelable.Creator<StatPoint>() {
        public StatPoint createFromParcel(Parcel in) {
            return new StatPoint(in);
        }

        public StatPoint[] newArray(int size) {
            return new StatPoint[size];
        }
    };
    private double sent, received;

    public StatPoint()
    {
        this(0.0,0.0);
    }
    public StatPoint(double sent, double received)
    {
        this.sent = sent;
        this.received = received;
    }

    public StatPoint(Parcel in) {
        double[] data = new double[2];

        in.readDoubleArray(data);
        sent = data[0];
        received = data[0];
    }

    @Override
    public String toString() {
        return ("(SENT: " + sent + ", RECEIVED: " + received + ")");
    }

    public double getSent() {
        return sent;
    }

    public void incrementSent(){sent++;}

    public double getReceived() {
        return received;
    }

    public void incrementReceived(){received++;}

    public double getTotal()
    {
        return sent+received;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDoubleArray(new double[]{sent, received});
    }
}