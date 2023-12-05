package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Datapoint {
    @SerializedName("x")
    private long timestamp;
    @SerializedName("y")
    private float value;

    private static List<Datapoint> datapoints=null;
    public static List<Datapoint> getDatapointList() {
        return datapoints;
    }
    public static void setDatapointList(List<Datapoint> datapoints) {
        Datapoint.datapoints = datapoints;
    }
    public Datapoint(long timestamp, float value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
