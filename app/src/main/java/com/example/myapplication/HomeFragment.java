package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.User;
import com.example.myapplication.Model.WeatherDevice;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {
    private HomeActivity parentActivity;
    TextView tv_username;
    ProgressBar pb_username;
    WeatherDevice weather;
    private View mView;
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
        tv_username.setText(User.getMe().username);
        tv_username.setVisibility(View.VISIBLE);
        pb_username=mView.findViewById(R.id.pb_username);
        pb_username.setVisibility(View.GONE);

        TextView tv_dow = mView.findViewById(R.id.tv_dow);
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        tv_dow.setText(sdf.format(new Date()));

        weather = new WeatherDevice(Device.getDeviceById("5zI6XqkQVSfdgOrZ1MyWEf"));
        TextView tv_temper = mView.findViewById(R.id.tv_temper);
        String temper = String.join("",weather.temperature.getValueString(), getResources().getString(R.string.celsius));
        tv_temper.setText(temper);

        TextView tv_wind = mView.findViewById(R.id.tv_windspeed);
        tv_wind.setText(weather.windSpeed.getValueString());

        TextView tv_rain = mView.findViewById(R.id.tv_rainfall);
        tv_rain.setText(weather.rainfall.getValueString());

        TextView tv_hum = mView.findViewById(R.id.tv_humidity);
        tv_hum.setText(weather.humidity.getValueString());
    }

}