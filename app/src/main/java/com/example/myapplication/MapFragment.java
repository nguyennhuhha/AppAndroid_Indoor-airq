package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.Map;
import com.example.myapplication.Model.Device;
import com.example.myapplication.RestAPI.APIManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapFragment extends Fragment {
    private HomeActivity parentActivity;
    private View mView;
    private GoogleMap mapView;
    private SupportMapFragment supportMapFragment;

    public MapFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_maps, container, false);
        parentActivity = (HomeActivity) getActivity();
        return mView;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitViews(view);
        InitEvents();
        //        GPS
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling   ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
            return;
        }

        //setMapView();
        new Thread(() -> {
            while (!Map.isReady) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            parentActivity.runOnUiThread(this::setMapView);

        }).start();
    }
    private void InitViews(View view) {
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        bs_device = view.findViewById(R.id.bs_device);
//        rv_attributes = view.findViewById(R.id.rv_attributes);
//        tvAssetName = view.findViewById(R.id.tv_assetName);
//        ivIcon = view.findViewById(R.id.iv_assetIcon);
//        pbLoading = view.findViewById(R.id.pb_loading_4);
//        sheetBehavior = BottomSheetBehavior.from(bs_device);
    }

    private void InitEvents() {
    }

    @SuppressLint("MissingPermission")
    private void setMapView() {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                mapView = map;
                mapView.setMyLocationEnabled(true);

                //event click marker
                mapView.setOnMarkerClickListener(marker -> {
                    Device device = (Device) marker.getTag();
                    return false;
                });

                mapView.getUiSettings().setZoomControlsEnabled(true);
                mapView.getUiSettings().setRotateGesturesEnabled(true);

                for (Device device : Device.getDevicesList()) {
                    BitmapDescriptor bitmap = device.getIconPinBitmap(parentActivity, device.getIconRes(device.type));
                    if (device.getPoint() == null) continue;

                    JsonObject o = new JsonObject();
                    o.addProperty("id", device.id);

                    // Add device markers to the map
                    MarkerOptions markerOptions = new MarkerOptions()
                            .title(device.name)
                            .position(device.getPoint())
                            .icon(bitmap);

                    Marker marker = mapView.addMarker(markerOptions);
                    marker.setTag(device);

                    //move camera to marker
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(device.getPoint(), 18);
//                    mapView.animateCamera(cameraUpdate);
                }
                mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(APIManager.center, APIManager.zoom));
                mapView.setMinZoomPreference(APIManager.min);
                mapView.setMaxZoomPreference(APIManager.max);
                //mapView.setLatLngBoundsForCameraTarget(mapModel.getBounds());
            }

        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // If request is cancelled, the result arrays are empty.
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setMapView();
        }
    }
}
