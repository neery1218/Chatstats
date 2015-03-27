package com.radiance.chatstats;

import com.github.mikephil.charting.utils.ValueFormatter;

/**
 * Created by Neerajen on 14/03/2015.
 */
public class MaxValueFormatter implements ValueFormatter {
    private int max;

    public MaxValueFormatter() {
        max = 0;
    }

    public int getMax() {
        return max;
    }

    @Override
    public String getFormattedValue(float value) {
        if (value > max)
            max = (int) value;
        return "";

    }
}
