package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ResetPass extends AppCompatActivity {
    private Button rsback,reset_btn;
    private EditText et_username;
    ProgressBar pb;
    TextView noti;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpw1);

        rsback = (Button) findViewById(R.id.back_btn);
        reset_btn = (Button) findViewById(R.id.resetpw_button);
        et_username = findViewById(R.id.repw_username);
        pb = findViewById(R.id.progress);
        noti = findViewById(R.id.noti_account1);
        webView = findViewById(R.id.web_reset);
        rsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPass.this, MainActivity.class);
                startActivity(intent);
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = et_username.getText().toString();
                if (username.length() > 0) {
                    doResetPwd(username, view);
                    pb.setVisibility(View.VISIBLE);
                    reset_btn.setVisibility(View.INVISIBLE);
                } else {
                    et_username.setError("Please enter your username");
                }
            }
        });
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void doResetPwd(String username, View view) {
        android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();

        String baseUrl = "https://uiot.ixxc.dev/";
        cookieManager.setAcceptCookie(true);
        cookieManager.acceptCookie();
        CookieManager.setAcceptFileSchemeCookies(true);
        CookieManager.getInstance().setAcceptCookie(true);
        cookieManager.getCookie(baseUrl);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDatabaseEnabled(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setGeolocationEnabled(false);
        webView.getSettings().setSaveFormData(false);
        String baseurl ="https://uiot.ixxc.dev/";
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("reset-credentials"))  {
                    String usrScript = "document.getElementById('username').value='" + username + "';";
                    view.evaluateJavascript(usrScript, null);
                    view.evaluateJavascript("document.getElementsByTagName('form')[0].submit();", null);
                    super.onPageFinished(view, url);
                }
                if (url.contains("login-actions/authenticate")) {
                    System.out.println("onPageFinished: " + url);
                    doResetSuccess();
                }
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        webView.loadUrl(baseurl+"auth/realms/master/login-actions/reset-credentials?client_id=openremote");
    }
    private void doResetSuccess() {
        noti.setText("You should receive an email shortly with further instructions.");
        noti.setTextColor(getResources().getColor(R.color.dark_greenlv1));
        pb.setVisibility(View.GONE);
        reset_btn.setVisibility(View.GONE);
    }
}