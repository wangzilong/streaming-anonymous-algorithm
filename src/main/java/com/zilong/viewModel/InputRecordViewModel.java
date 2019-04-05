package com.zilong.viewModel;

import com.zilong.model.InputRecord;

public class InputRecordViewModel {
    private int time;
    private double rawValue;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getRawValue() {
        return rawValue;
    }

    public void setRawValue(double rawValue) {
        this.rawValue = rawValue;
    }

    public InputRecord build(){
        return new InputRecord(time, rawValue);
    }
}
