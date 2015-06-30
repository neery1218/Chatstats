package com.radianceTOPS.chatstats;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by Richard on 2015-03-14.
 */
public class DateFrequency {
    private Date date;
    private int year, month, day;
    private int frequency = 0;

    public DateFrequency(Date date)
    {
        this.date = date;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    public DateFrequency(long time)
    {
        this(new Date(time));
    }

    public boolean inRange(long time)
    {
        Date nextDay = new Date(date.getTime()+1000*60*60*24);
        if (date.before(new Date(time)) && nextDay.after(new Date(time)))
        {
            return true;
        }

        else
            return false;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public Date getDate() {

        return date;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void incrementFrequency() {
        this.frequency++;
    }
}
