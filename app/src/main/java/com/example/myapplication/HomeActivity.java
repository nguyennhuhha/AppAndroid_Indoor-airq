package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.User;
import com.example.myapplication.Model.WeatherDevice;
import com.example.myapplication.RestAPI.APIManager;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeActivity extends AppCompatActivity {
    public AnimatedBottomBar navbar;
    private String usr_name, temp, rain, hum, wind;

    public HomeFragment homeFrag;
    private FragmentTransaction ft;
    private WeatherDevice defaultDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        usr_name = User.getMe().username;
        defaultDevice = new WeatherDevice(Device.getDevice());
        temp = defaultDevice.temperature.getValueString();
        rain = defaultDevice.rainfall.getValueString();
        hum = defaultDevice.humidity.getValueString();
        wind = defaultDevice.windSpeed.getValueString();
        senDatatoFragment();
    }
    private void senDatatoFragment(){
        homeFrag = new HomeFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame, homeFrag);
        ft.commit();
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