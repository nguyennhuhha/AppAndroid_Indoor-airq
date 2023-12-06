package com.example.myapplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Datapoint {
    @SerializedName("x")
    private long timestamp;
    @SerializedName("y")
    private float value;

    private static List<Datapoint> datapointRainfall=null;
    private static List<Datapoint> datapointTemperature=null;
    private static List<Datapoint> datapointHumidity=null;
    public static List<Datapoint> datapointWindspeed=null;

    public static List<Datapoint> getDatapointRainfallList() {
        return datapointRainfall;
    }
    public static void setDatapointRainfallList(List<Datapoint> datapoints) {
        Datapoint.datapointRainfall = datapoints;
    }
    public static List<Datapoint> getDatapointTemperatureList() {
        return datapointTemperature;
    }
    public static void setDatapointTemperatureList(List<Datapoint> datapoints) {
        Datapoint.datapointTemperature = datapoints;
    }
    public static List<Datapoint> getDatapointHumidityList() {
        return datapointHumidity;
    }
    public static void setDatapointHumidityList(List<Datapoint> datapoints) {
        Datapoint.datapointHumidity = datapoints;
    }
    public static List<Datapoint> getDatapointWindspeedList() {
        return datapointWindspeed;
    }
    public static void setDatapointWindspeedList(List<Datapoint> datapoints) {
        Datapoint.datapointWindspeed = datapoints;
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
