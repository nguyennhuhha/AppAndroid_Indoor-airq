package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.RestAPI.APIClient;
import com.example.myapplication.RestAPI.APIInterface;
import com.example.myapplication.RestAPI.tokenResponse;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        webView = findViewById(R.id.web_login);
        Intent intent = getIntent();
        String received_name = intent.getStringExtra("name");
        String received_pass = intent.getStringExtra("pass");
        CallLoginService(received_name, received_pass);
    }
    private void CallLoginService(String name, String pass){
        try{
            final String id = name;
            final String key = pass;
            APIInterface service = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> srvLogin = service.getToken("openremote",id,key,"password");
            srvLogin.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        try {
                            String ResponseJson = response.body().string();
                            Gson objGson = new Gson();
                            tokenResponse objResp=objGson.fromJson(ResponseJson, tokenResponse.class);
                            Toast.makeText(LoadingActivity.this, objResp.getAccess_token(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoadingActivity.this, "Token success", Toast.LENGTH_SHORT).show();
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(LoadingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(LoadingActivity.this, "Invalid, Try again", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoadingActivity.this, "System KH", Toast.LENGTH_SHORT).show();
                    Log.e("NetworkError", "Error: " + t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(LoadingActivity.this, "System error", Toast.LENGTH_SHORT).show();
        }
    }
}