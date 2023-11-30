package com.example.myapplication;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.Map;
import com.example.myapplication.Model.User;
import com.example.myapplication.Model.WeatherDevice;
import com.example.myapplication.RestAPI.APIManager;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fm;
    public AnimatedBottomBar navbar;
    private String usr_name, temp, rain, hum, wind;

    public HomeFragment homeFrag;
    public MapFragment mapFrag;
    public ChartFragment chartFrag;
    private FragmentTransaction ft;
    private WeatherDevice defaultDevice;
    private Fragment fragment;
    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                navbar.selectTabAt(0, true);
//            }
//        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usr_name = User.getMe().username;
        defaultDevice = new WeatherDevice(Device.getDevice());
        temp = defaultDevice.temperature.getValueString();
        rain = defaultDevice.rainfall.getValueString();
        hum = defaultDevice.humidity.getValueString();
        wind = defaultDevice.windSpeed.getValueString();

        InitVars();
        InitViews();
        InitEvents();

        fm.beginTransaction().add(R.id.main_frame, mapFrag, "map").commit();
        fm.beginTransaction().hide(mapFrag).commit();

        fm.beginTransaction().add(R.id.main_frame, chartFrag, "chart").commit();
        fm.beginTransaction().hide(chartFrag).commit();

        fm.beginTransaction().add(R.id.main_frame, homeFrag, "home").commit();
        fragment = homeFrag;
        navbar.selectTabAt(0, false);
        //senDatatoFragmentHome();
    }
//    private void senDatatoFragmentHome(){
//        ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.main_frame, homeFrag);
//        ft.commit();
//    }
    private void InitVars() {
        selectedIndex = 0;

        fm = getSupportFragmentManager();

        homeFrag = new HomeFragment();
        mapFrag = new MapFragment();
        chartFrag= new ChartFragment();
    }
    private void InitViews() {
        navbar = findViewById(R.id.bottom_bar);
    }

    private void InitEvents() {
        navbar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                //if (fragment == devicesFrag) devicesFrag.changeSelectedDevice(-1, "");

                switch (newIndex) {
                    case 0:
                        fm.beginTransaction().hide(fragment).commit();
                        fragment = homeFrag;
                        break;
                    case 1:
                        fm.beginTransaction().hide(fragment).commit();
                        fragment = mapFrag;
                        break;
                    case 2:
                        fm.beginTransaction().hide(fragment).commit();
                        fragment = chartFrag;
                        break;
                }
                fm.beginTransaction()
                        .show(fragment)
                        .commit();
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }

    public String getUsr_name() {
        return usr_name;
    }

    public String getTemp() {
        return temp;
    }

    public String getRain() {
        return rain;
    }

    public String getHum() {
        return hum;
    }

    public String getWind() {
        return wind;
    }
}