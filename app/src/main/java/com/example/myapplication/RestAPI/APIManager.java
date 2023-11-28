package com.example.myapplication.RestAPI;

import com.example.myapplication.LoadingActivity;
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

    public static void getUserInfo() {
        Call<User> call = userAI.getUserInfo();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("API CALL", response.code()+"");
                User.setMe(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("API CALL", t.getMessage().toString());
            }
        });
    }
}
