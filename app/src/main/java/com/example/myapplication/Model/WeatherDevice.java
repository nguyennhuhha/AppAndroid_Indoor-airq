package com.example.myapplication.Model;

import com.google.gson.Gson;

public class WeatherDevice extends Device {
    public Attribute temperature;
    public Attribute humidity;
    public Attribute windSpeed;
    public Attribute rainfall;
    public WeatherDevice(Device device) {
        if (!device.type.equals("WeatherAsset")) {
            throw new IllegalArgumentException("Device type is not WeatherAsset");
        }

        this.temperature = new Gson().fromJson(device.attributes.get("temperature"), Attribute.class);
        this.humidity = new Gson().fromJson(device.attributes.get("humidity"), Attribute.class);
        this.rainfall = new Gson().fromJson(device.attributes.get("rainfall"), Attribute.class);
        this.windSpeed = new Gson().fromJson(device.attributes.get("windSpeed"), Attribute.class);
    }
}
