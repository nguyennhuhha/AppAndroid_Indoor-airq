package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.BottomSheetAdapter;
import com.example.myapplication.Model.Attribute;
import com.example.myapplication.Model.Map;
import com.example.myapplication.Model.Device;
import com.example.myapplication.API.APIManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

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

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        // GPS
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
            return;
        }
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
                    String id = device.id;
                    openDialog(id);
                    return false;
                });

                mapView.getUiSettings().setZoomControlsEnabled(true);
                mapView.getUiSettings().setRotateGesturesEnabled(true);

                for (Device device : Device.getDevicesList()) {
                    BitmapDescriptor bitmap = device.getIconPinBitmap(parentActivity, device.getIconRes(device.type));
                    if (device.getPoint() == null) continue;

                    // Add device markers to the map
                    MarkerOptions markerOptions = new MarkerOptions()
                            .title(device.name)
                            .position(device.getPoint())
                            .icon(bitmap);

                    Marker marker = mapView.addMarker(markerOptions);
                    marker.setTag(device);

                }
                //camera zoom
                mapView.moveCamera(CameraUpdateFactory.zoomTo(APIManager.zoom));

                // box zoom
                mapView.getUiSettings().setCompassEnabled(APIManager.box);
                //camera position
                mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(APIManager.center, APIManager.zoom+1));

                //min max zoom
                mapView.setMinZoomPreference(APIManager.min);
                mapView.setMaxZoomPreference(APIManager.max);
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
    private void openDialog(@NonNull String assetId) {
        Device device = Device.getDeviceById(assetId);

        if (device != null) {
            Dialog dialog = new Dialog(parentActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet);

            Window window = dialog.getWindow();
            if(window==null){
                return;
            }

            List<Attribute> attributes = device.getRequiredAttributes();
            BottomSheetAdapter adapter = new BottomSheetAdapter(attributes);
            LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext());

            TextView tvAssetName = dialog.findViewById(R.id.tv_Name);
            ImageView ivIcon = dialog.findViewById(R.id.iv_Icon);
            RecyclerView rv_attributes = dialog.findViewById(R.id.rv_attributes);

            tvAssetName.setText(device.name);

            ivIcon.setImageResource(device.getIconRes(device.type));

            rv_attributes.setLayoutManager(layoutManager);
            rv_attributes.setAdapter(adapter);

            dialog.show();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
            window.setGravity(Gravity.BOTTOM);

        }
    }
}
