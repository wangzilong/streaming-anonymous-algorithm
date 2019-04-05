package com.zilong.model;

public class InputRecord implements Comparable<InputRecord>{

    /** The time that this record was received by the filter, in seconds from the start time */
    private final int time;
    private final double rawValue;

    public InputRecord(int time, double rawValue) {
        this.time = time;
        this.rawValue = rawValue;
    }

    public int getTime() {
        return time;
    }

    public double getRawValue() {
        return rawValue;
    }

    @Override
    public String toString() {
        return time + "," + rawValue;
    }

    @Override
    public int compareTo(InputRecord o) {
        if (rawValue < o.getRawValue()) return -1;
        if (rawValue > o.getRawValue()) return 1;
        return 0;
    }
}
