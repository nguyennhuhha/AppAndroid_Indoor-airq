package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.User;
import com.example.myapplication.Model.WeatherDevice;
import com.example.myapplication.RestAPI.APIManager;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

public class HomeFragment extends Fragment {
    private HomeActivity parentActivity;
    ShimmerFrameLayout shimmerFrameLayout;
    ScrollView scrollView;
    LinearLayout layout_main;
    ConstraintLayout layout_top;
    TextView tv_username;
    ProgressBar pb_username;
    WeatherDevice defaultDevice;
    User me;
    private View mView;
    String user;

    public HomeFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        parentActivity = (HomeActivity) getActivity();
        showBasicInfo();
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void showBasicInfo(){
        tv_username=mView.findViewById(R.id.tv_username);
        //tv_username.setText(parentActivity.getUsr_name());
        tv_username.setText(User.getMe().username);
        tv_username.setVisibility(View.VISIBLE);
        pb_username=mView.findViewById(R.id.pb_username);
        pb_username.setVisibility(View.GONE);

        TextView tv_dow = mView.findViewById(R.id.tv_dow);
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        tv_dow.setText(sdf.format(new Date()));

        defaultDevice = new WeatherDevice(Device.getDevice());
        TextView tv_temper = mView.findViewById(R.id.tv_temper);
        //String temper = String.join("", parentActivity.getTemp(), getResources().getString(R.string.celsius));
        String temper = String.join("",defaultDevice.temperature.getValueString(), getResources().getString(R.string.celsius));
        tv_temper.setText(temper);

        TextView tv_wind = mView.findViewById(R.id.tv_windspeed);
        //tv_wind.setText(parentActivity.getWind());
        tv_wind.setText(defaultDevice.windSpeed.getValueString());

        TextView tv_rain = mView.findViewById(R.id.tv_rainfall);
        //tv_rain.setText(parentActivity.getRain());
        tv_rain.setText(defaultDevice.rainfall.getValueString());

        TextView tv_hum = mView.findViewById(R.id.tv_humidity);
        //tv_hum.setText(parentActivity.getHum());
        tv_hum.setText(defaultDevice.humidity.getValueString());
    }

}