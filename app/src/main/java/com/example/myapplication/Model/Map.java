package com.example.myapplication.Model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
//import com.mapbox.geojson.Point;
//import com.mapbox.maps.CoordinateBounds;

import java.util.ArrayList;

public class Map {
    public static boolean isReady = false;

    public static Map MapObj;

    @SerializedName("options")
    public JsonObject options;
    @SerializedName("version")
    public int version;

    public LatLng getCenter() {
        float lat = options.get("default").getAsJsonObject().get("center").getAsJsonArray().get(1).getAsFloat();
        float lon = options.get("default").getAsJsonObject().get("center").getAsJsonArray().get(0).getAsFloat();
        LatLng latLng = new LatLng(lat, lon);
        return latLng;
    }
    public static Map getMapObj() {
    return MapObj;
}

    public static void setMapObj(Map mapObj) {
        MapObj = mapObj;
        isReady = true;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public float getZoom() {
        return options.get("default").getAsJsonObject().get("zoom").getAsFloat();
    }

    public float getMinZoom() {
        return options.get("default").getAsJsonObject().get("minZoom").getAsFloat();
    }

    public float getMaxZoom() {
        return options.get("default").getAsJsonObject().get("maxZoom").getAsFloat();
    }
    public boolean getBoxZoom() {
        return options.get("default").getAsJsonObject().get("boxZoom").getAsBoolean();
    }
    public ArrayList<Double> getBounds() {
        ArrayList<Double> bounds = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bounds.add(options.get("default").getAsJsonObject().get("bounds").getAsJsonArray().get(i).getAsDouble());
        }
        return bounds;
    }
}
