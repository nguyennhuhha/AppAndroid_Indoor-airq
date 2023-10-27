package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button signup;
    private TextView reset_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.signin_btn);
        signup = (Button) findViewById(R.id.signup_btn);
        reset_pass = (TextView) findViewById(R.id.resetpwtext);

        //reset pass
        String text = "Reset your password?";
        SpannableString ss = new SpannableString(text);
        ClickableSpan c1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //mở trang để reset password
            }
        };
        ss.setSpan(c1, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        reset_pass.setText(ss);
        reset_pass.setMovementMethod(LinkMovementMethod.getInstance());

        //mở trang login
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, MainActivity2.class);
                int data = 0;
                it.putExtra("key", data);
                startActivity(it);
            }
        });
        //mở trang signup
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, MainActivity2.class);
                int data = 1;
                it.putExtra("key", data);
                startActivity(it);
            }
        });




    }
}