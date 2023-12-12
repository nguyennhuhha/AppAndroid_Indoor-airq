package com.example.myapplication.API;

import com.example.myapplication.Model.Datapoint;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.Map;
import com.example.myapplication.Model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import android.util.Log;

public class APIManager {
    public static LatLng center;
    public static float zoom, min, max;
    public static ArrayList<Double> bounds;
    public static Boolean box;
    private static final APIClient apiClient = new APIClient();
    private static final APIClient1 apiClient1 = new APIClient1();
    private static final APIInterface userAI = apiClient.getClient().create(APIInterface.class);
    private static final APIInterface userAI1 = apiClient1.getClient().create(APIInterface.class);

    //get user
    public static void getUserInfo() {
        Call<User> call = userAI1.getUserInfo();
        try {
            Response<User> response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                Log.d("API USER", response.code() + "");
                User.setMe(response.body());
                Log.d("Name", User.getMe().username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get map
    public static void getMap() {
        Call<Map> call = userAI.getMap();
        try {
            Response<Map> response = call.execute();
            if (response.isSuccessful()&& response.code() == 200) {
                Log.d("API MAP", response.code()+"");
                Map.setMapObj(response.body());
                center = Map.getMapObj().getCenter();
                zoom = Map.getMapObj().getZoom();
                min = Map.getMapObj().getMinZoom();
                max = Map.getMapObj().getMaxZoom();
                bounds=Map.getMapObj().getBounds();
                box = Map.getMapObj().getBoxZoom();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void queryDevices(JsonObject body) {
        Call<List<Device>> call = userAI.queryDevices(body);
        try {
            Response<List<Device>> response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                List<Device> deviceList = response.body();
                Device.setDevicesList(deviceList);
            } else {
                Device.setDevicesList(null);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    //datapoint
    public static void getDataPointRainfall(String deviceID,String attributeName,JsonObject body) {
        Call<List<Datapoint>> call = userAI.getDataPoint(deviceID,attributeName, body);
        try {
            Response<List<Datapoint>> response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                List<Datapoint> datapoints = response.body();
                Datapoint.setDatapointRainfallList(datapoints);
                Log.d("API DATAPOINT_RAINFALL", String.valueOf(response.code()));
            } else {
                Datapoint.setDatapointRainfallList(null);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void getDataPointTemperature(String deviceID,String attributeName,JsonObject body) {
        Call<List<Datapoint>> call = userAI.getDataPoint(deviceID,attributeName, body);
        try {
            Response<List<Datapoint>> response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                List<Datapoint> datapoints = response.body();
                Datapoint.setDatapointTemperatureList(datapoints);
                Log.d("API DATAPOINT_TEMPERATURE", String.valueOf(response.code()));
            } else {
                Datapoint.setDatapointTemperatureList(null);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }public static void getDataPointHumidity(String deviceID,String attributeName,JsonObject body) {
        Call<List<Datapoint>> call = userAI.getDataPoint(deviceID,attributeName, body);
        try {
            Response<List<Datapoint>> response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                List<Datapoint> datapoints = response.body();
                Datapoint.setDatapointHumidityList(datapoints);
                Log.d("API DATAPOINT_Humidity", String.valueOf(response.code()));
            } else {
                Datapoint.setDatapointHumidityList(null);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
    public static void getDataPointWindSpeed(String deviceID,String attributeName,JsonObject body) {
        Call<List<Datapoint>> call = userAI.getDataPoint(deviceID,attributeName, body);
        try {
            Response<List<Datapoint>> response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                List<Datapoint> datapoints = response.body();
                Datapoint.setDatapointWindspeedList(datapoints);
                Log.d("API DATAPOINT_WindSpeed", String.valueOf(response.code()));
            } else {
                Datapoint.setDatapointWindspeedList(null);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

}
