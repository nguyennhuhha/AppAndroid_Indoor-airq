package com.example.myapplication.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import com.example.myapplication.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import com.google.android.gms.maps.model.LatLng;

public class Device {
    @SerializedName("id")
    public String id;
    @SerializedName("version")
    public String version;
    @SerializedName("name")
    public String name;
    @SerializedName("type")
    public String type;
    @SerializedName("attributes")
    public JsonObject attributes;
    @SerializedName("path")
    public ArrayList<String> path;
    private static final List<Device> deviceList = new ArrayList<>();

    public static List<Device> getDevicesList() {
        return deviceList;
    }

    public static void setDevicesList(List<Device> list) {
        if (list == null) return;

        deviceList.clear();

        if (list.get(0).type.equals("GroupAsset") && list.get(0).name.equals("Consoles")) list.remove(0);

        for (Device device : list) {
            if (!device.type.contains("ConsoleAsset")) {
                deviceList.add(device);
            }
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }
    public static Device getDeviceById(String id) {
        for (Device device : deviceList) {
            if (device.id.equals(id)) {
                return device;
            }
        }
        return null;
    }

    // Get attributes
    public List<Attribute> getRequiredAttributes() {
        List<Attribute> attributeList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : attributes.entrySet()) {
//            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (value.isJsonObject()) {
                JsonObject o = value.getAsJsonObject();
                Attribute attribute = new Gson().fromJson(o, Attribute.class);

                // Kiểm tra điều kiện
                if (!attribute.value.isJsonObject()) {
                    attributeList.add(attribute);
                }
            }
        }

        return attributeList;
    }

    // Get device location
    public LatLng getPoint() {
        try {
            float lon = attributes.get("location").getAsJsonObject().get("value").getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsFloat();
            float lat = attributes.get("location").getAsJsonObject().get("value").getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsFloat();
            LatLng latLng = new LatLng(lat, lon);
            return latLng;
        } catch (Exception e) {
            return null;
        }
    }

    // Get icon resource id
    public int getIconRes(String deviceType) {
        switch (deviceType) {
            case "GroupAsset":
                break;
            case "WeatherAsset":
                return R.drawable.ic_weather;
        }
        return R.drawable.baseline_lightbulb_24;
    }

    // Get icon bitmap (show on Maps)
    public BitmapDescriptor getIconPinBitmap(Context context, int resId) {
        // Get icon drawable from resId
        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), resId, null);
        assert drawable != null;

        // Get pin drawable
        Drawable pin_drawable = null;
        if (resId == R.drawable.baseline_lightbulb_24){
            pin_drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_pin_pink, null);
        }
        else{
            pin_drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_pin_green, null);
        }
        assert pin_drawable != null;

        // Draw icon into pin
        Bitmap pin = Bitmap.createBitmap(pin_drawable.getIntrinsicWidth(), pin_drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(pin);
        pin_drawable.setBounds(0, 0, pin_drawable.getIntrinsicWidth(), pin_drawable.getIntrinsicHeight());
        pin_drawable.draw(canvas);

        drawable.setBounds(6, 6, drawable.getIntrinsicWidth() - 18, drawable.getIntrinsicHeight() - 18);
        drawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(pin);
    }

}
