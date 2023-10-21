package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.signin_btn);
        signup = (Button) findViewById(R.id.signup_btn);
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