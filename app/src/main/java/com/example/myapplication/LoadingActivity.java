package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.API.APIClient;
import com.example.myapplication.API.APIClient1;
import com.example.myapplication.API.APIInterface;
import com.example.myapplication.API.Token;
import com.google.gson.Gson;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView img;
    private AsyncTasks async;
    private ProgressBar progressBar;
    private String baseurl = "https://uiot.ixxc.dev";

    @Override
    //quay trở về trang trước
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        img = findViewById(R.id.back_btn);
        webView = findViewById(R.id.web_login);
        progressBar=findViewById(R.id.progress);

        img.setOnClickListener(new View.OnClickListener() {//quay về trang trước
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        Intent intent = getIntent();
        //1: login, 2: signup
        int received = intent.getIntExtra("fragment", 0);
        if (received==1){//login
            String received_name = intent.getStringExtra("name");
            String received_pass = intent.getStringExtra("pass");
            CallLoginService(received_name, received_pass);
            img.setVisibility(View.VISIBLE);
        }
        else{//sign up
            String usr = intent.getStringExtra("signup_usr");
            String email = intent.getStringExtra("signup_email");
            String pwd = intent.getStringExtra("signup_pass");
            String rePwd=intent.getStringExtra("signup_cfmpass");
            img.setVisibility(View.GONE);
            CookieManager cm = CookieManager.getInstance();
            cm.removeAllCookie();

            webView.getSettings().setJavaScriptEnabled(true);

            //webView.setWebViewClient(new WebViewClient());
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    // Tìm và nhấn vào nút sau khi trang đã tải xong
                    if (url.contains(baseurl + "/auth/realms/master/protocol/openid-connect/auth?client_id=openremote")) {
                        // Thực hiện click vào nút SIGN UP khi địa chỉ URL khớp
                        webView.loadUrl("javascript:(function(){" +
                                "document.getElementsByClassName('btn waves-effect waves-light')[1].click();" +
                                "})()");
                        webView.setWebViewClient(new WebViewClient(){
                            @Override
                            public void onPageFinished(WebView view, String url) {
                                String usrScript = "document.getElementById('username').value='" + usr + "';";
                                String emailScript = "document.getElementById('email').value='" + email + "';";
                                String pwdScript = "document.getElementById('password').value='" + pwd + "';";
                                String rePwdScript = "document.getElementById('password-confirm').value='" + rePwd + "';";

                                view.evaluateJavascript(usrScript, null);
                                view.evaluateJavascript(emailScript, null);
                                view.evaluateJavascript(pwdScript, null);
                                view.evaluateJavascript(rePwdScript, null);
                                view.evaluateJavascript("document.getElementById('kc-register-form').submit();", null);
                                CallSignUpService(usr, pwd);
                            }
                        });
                    }
                }
            });
            webView.loadUrl(baseurl);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.clearHistory();
            webView.clearFormData();
            webView.clearSslPreferences();
        }

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
                            Token objResp=objGson.fromJson(ResponseJson, Token.class);
                            APIClient1.token = objResp.getAccess_token();
                            //Toast.makeText(LoadingActivity.this, "Token success", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(LoadingActivity.this, APIClient1.token, Toast.LENGTH_SHORT).show();
                            TextView text = findViewById(R.id.text_wait);
                            text.setText("Success");

                            Executor executor = Executors.newSingleThreadExecutor();
                            CompletableFuture.supplyAsync(() -> {
                                async = new AsyncTasks(LoadingActivity.this);
                                async.execute();
                                try {
                                    // Gọi get() để đợi AsyncTask hoàn thành và trả về kết quả
                                    return async.get();
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }, executor).thenAccept(result -> {
                                // Xử lý kết quả ở đây sau khi AsyncTask hoàn thành
                                if ("done".equals(result)){
                                    Log.d("Done Async", "Finish");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setVisibility(View.GONE);
                                            Intent it = new Intent(LoadingActivity.this, HomeActivity.class);
                                            startActivity(it);
                                        }
                                    });
                                }
                            });
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(LoadingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("Error", "API error");
                        }
                    }
                    else{
                        Toast.makeText(LoadingActivity.this, "Invalid, Try again", Toast.LENGTH_SHORT).show();
                        TextView text = findViewById(R.id.text_wait);
                        text.setText("Login Failed \n Please try again!");
                        progressBar.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoadingActivity.this, "System error", Toast.LENGTH_SHORT).show();
                    Log.e("NetworkError", "Error: " + t.getMessage());
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(LoadingActivity.this, "System error", Toast.LENGTH_SHORT).show();
        }

    }
    private void CallSignUpService(String name, String pass){
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
                            Token objResp=objGson.fromJson(ResponseJson, Token.class);
                            APIClient1.token = objResp.getAccess_token();
                            TextView text = findViewById(R.id.text_wait);
                            text.setText("Success");

                            Executor executor = Executors.newSingleThreadExecutor();
                            CompletableFuture.supplyAsync(() -> {
                                async = new AsyncTasks(LoadingActivity.this);
                                async.execute();
                                try {
                                    // Gọi get() để đợi AsyncTask hoàn thành và trả về kết quả
                                    return async.get();
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }, executor).thenAccept(result -> {
                                // Xử lý kết quả ở đây sau khi AsyncTask hoàn thành
                                if ("done".equals(result)){
                                    Log.d("Done Async", "Finish");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setVisibility(View.GONE);
                                            Intent it = new Intent(LoadingActivity.this, HomeActivity.class);
                                            startActivity(it);
                                        }
                                    });
                                }
                            });
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(LoadingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("Error", "API error");
                        }
                    }
                    else{
                        Log.d("Sign Up", "Invalid, Try again");
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(LoadingActivity.this, "System error", Toast.LENGTH_SHORT).show();
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
