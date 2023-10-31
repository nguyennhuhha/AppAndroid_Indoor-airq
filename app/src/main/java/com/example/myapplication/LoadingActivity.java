package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.RestAPI.APIClient;
import com.example.myapplication.RestAPI.APIInterface;
import com.example.myapplication.RestAPI.tokenResponse;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView img;

    @Override
    //quay trở về trang trước
    public void onBackPressed() {
        super.onBackPressed();
        // Thực hiện các hành động cần thiết khi quay lại trang trước
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        webView = findViewById(R.id.web_login);
        webView.getSettings().setJavaScriptEnabled(true);
        img = findViewById(R.id.back_btn);
        img.setOnClickListener(new View.OnClickListener() {//quay về trang trước
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        //1: login, 2: signup
        int received = intent.getIntExtra("fragment", 0);
        if (received==1){//login
            String received_name = intent.getStringExtra("name");
            String received_pass = intent.getStringExtra("pass");
            CallLoginService(received_name, received_pass);
        }
        else{//sign up
            String usr = intent.getStringExtra("signup_usr");
            String email = intent.getStringExtra("signup_email");
            String pwd = intent.getStringExtra("signup_pass");
            String rePwd=intent.getStringExtra("signup_cfmpass");

            String url ="https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/auth?client_id=openremote&redirect_uri=https%3A%2F%2Fuiot.ixxc.dev%2Fmanager%2F&state=b2205ccc-4679-4c2b-97c5-bdfdf3567e37&response_mode=fragment&response_type=code&scope=openid&nonce=f75830f5-71af-4d8d-bf2e-19723e1548b2";
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    // Khi trang web đã tải xong
                    // Tìm và nhấn nút SIGN UP
                    //super.onPageFinished(view, url);
                    webView.loadUrl("javascript:(function(){" +
                            "var elements = document.getElementsByClassName('btn waves-effect waves-light');" +
                            "for (var i = 0; i < elements.length; i++) {" +
                            "    var element = elements[i];" +
                            "    if (element.innerText.includes('SIGN UP')) {" +
                            "        element.click();" + // Nhấn vào phần tử <a>
                            "    }" +
                            "}" +
                            "})()");
                    String usrScript = "document.getElementById('username').value='" + usr + "';";
                    String emailScript = "document.getElementById('email').value='" + email + "';";
                    String pwdScript = "document.getElementById('password').value='" + pwd + "';";
                    String rePwdScript = "document.getElementById('password-confirm').value='" + rePwd + "';";

                    view.evaluateJavascript(usrScript, null);
                    view.evaluateJavascript(emailScript, null);
                    view.evaluateJavascript(pwdScript, null);
                    view.evaluateJavascript(rePwdScript, null);
                    view.evaluateJavascript("document.getElementById('kc-register-form').submit();", null);
                    CallLoginService(usr, pwd);
                }
            });

        }
    }
    private void CallLoginService(String name, String pass){
        ProgressBar progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
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
                            TextView text = findViewById(R.id.text_wait);
                            text.setText("Success");
                            progressBar.setVisibility(View.GONE);
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
