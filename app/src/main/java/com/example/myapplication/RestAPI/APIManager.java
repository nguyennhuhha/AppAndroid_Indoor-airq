package com.example.myapplication.RestAPI;

import com.example.myapplication.LoadingActivity;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.Map;
import com.example.myapplication.Model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import android.util.Log;
import android.widget.Toast;

public class APIManager {
    private static final APIClient apiClient = new APIClient();
    private static final APIInterface userAI = apiClient.getClient().create(APIInterface.class);

    //get user
    public static void getUserInfo() {
        Call<User> call = userAI.getUserInfo();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("API USER", response.code()+"");
                User.setMe(response.body());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("API USER", t.getMessage().toString());
            }
        });
    }

    //get temp rain hum
    public static void getDevice(){
        Call<Device> call = userAI.getDevice("5zI6XqkQVSfdgOrZ1MyWEf");
        call.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                Log.d("API DEVICE", response.code()+"");
                Device.setDevice(response.body());
            }
            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Log.d("API DEVICE", t.getMessage().toString());
            }
        });
    }

    //get map
    public static void getMap() {
        Call<Map> call = userAI.getMap();
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {
                Log.d("API MAP", response.code()+"");
                Map.setMapObj(response.body());
            }
            @Override
            public void onFailure(Call<Map> call, Throwable t) {
                Log.d("API MAP", t.getMessage().toString());
            }
        });
//        try {
//            Response<Map> response = call.execute();
//            if (response.isSuccessful()) { Map.setMapObj(response.body()); }
//        } catch (IOException e) { e.printStackTrace(); }
    }
}
