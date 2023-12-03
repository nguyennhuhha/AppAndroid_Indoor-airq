package com.example.myapplication.RestAPI;

import com.example.myapplication.LoadingActivity;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.Map;
import com.example.myapplication.Model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import android.util.Log;
import android.widget.Toast;

public class APIManager {
    public static LatLng center;
    public static float zoom, min, max;
    public static ArrayList<Double> bounds;
    private static final APIClient apiClient = new APIClient();
    private static final APIInterface userAI = apiClient.getClient().create(APIInterface.class);

    //get user
    public static void getUserInfo() {
        Call<User> call = userAI.getUserInfo();
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                Log.d("API USER", response.code()+"");
//                User.setMe(response.body());
//            }
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.d("API USER", t.getMessage().toString());
//            }
//        });
        try {
            Response<User> response = call.execute();
            if (response.isSuccessful()&& response.code() == 200) {
                Log.d("API USER", response.code()+"");
                User.setMe(response.body());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    //get temp rain hum
    public static void getDevice(){
        Call<Device> call = userAI.getDevice("5zI6XqkQVSfdgOrZ1MyWEf");
//        call.enqueue(new Callback<Device>() {
//            @Override
//            public void onResponse(Call<Device> call, Response<Device> response) {
//                Log.d("API DEVICE", response.code()+"");
//                Device.setDevice(response.body());
//            }
//            @Override
//            public void onFailure(Call<Device> call, Throwable t) {
//                Log.d("API DEVICE", t.getMessage().toString());
//            }
//        });
        try {
            Response<Device> response = call.execute();
            if (response.isSuccessful()&& response.code() == 200) {
                Log.d("API DEVICE", response.code()+"");
                Device.setDevice(response.body());
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    //get map
    public static void getMap() {
        Call<Map> call = userAI.getMap();
//        call.enqueue(new Callback<Map>() {
//            @Override
//            public void onResponse(Call<Map> call, Response<Map> response) {
//                Log.d("API MAP", response.code()+"");
//                Map.setMapObj(response.body());
//            }
//            @Override
//            public void onFailure(Call<Map> call, Throwable t) {
//                Log.d("API MAP", t.getMessage().toString());
//            }
//        });
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
}
