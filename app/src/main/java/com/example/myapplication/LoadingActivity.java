package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        //thực hiện các thao tác chạy nền của WebView khi hiển thị màn hình Loading Screen
        //WebViewTask webViewTask = new WebViewTask();
        //webViewTask.execute();

        //tạo 1 class để chạy nền
        //private class WebViewTask extends AsyncTask<Void, Void, Void> {
        //    @Override
        //    protected void onPreExecute() {
        //        super.onPreExecute();
        //        // Hiển thị trang Loading Screen ở đây
        //    }
        //
        //    @Override
        //    protected Void doInBackground(Void... params) {
        //        // Thực hiện các thao tác của WebView ở đây
        //        // Ví dụ: tạo WebView, tải trang web, thực hiện JavaScript, ...
        //        WebView webView = new WebView(getApplicationContext());
        //        webView.getSettings().setJavaScriptEnabled(true);
        //        webView.loadUrl("https://example.com");
        //        // Thêm các thao tác khác ở đây
        //
        //        // Đợi cho WebView kết thúc (hoặc khi cần dừng thủ công)
        //        // Ví dụ: sử dụng một biến cờ (flag) để kiểm tra
        //        while (!webViewIsDone) {
        //            // Kiểm tra điều kiện dừng
        //        }
        //
        //        return null;
        //    }
        //
        //    @Override
        //    protected void onPostExecute(Void result) {
        //        super.onPostExecute(result);
        //        // Ẩn trang Loading Screen và xử lý kết quả khi công việc hoàn thành
        //    }
        //}
    }
}