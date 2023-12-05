package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.Model.Datapoint;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.Map;
import com.example.myapplication.Model.Token;
import com.example.myapplication.Model.User;
import com.example.myapplication.RestAPI.APIManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AsyncTasks extends AsyncTask<String, Long, String> {
    private ProgressDialog pdWaiting;
    TextView textView;
    public static float minzoom;
    public static float maxzoom;
    public static float zoom;
    private Context context;
    public AsyncTasks(Context context){
        this.context = context;
    }
    @Override
    protected  void onPreExecute(){
        super.onPreExecute();

        //use UI thread here
//        pdWaiting = new ProgressDialog(this.context);
//        pdWaiting.setMessage(this.context.getString(R.string.wait_loading));
//        pdWaiting.show();
    }
    @Override
    protected  String doInBackground(String... params){
        //Call API
        if (User.getMe() == null) {
            APIManager.getUserInfo();
        }
        if (Device.getDevice() == null ) {
            APIManager.getDevice();
        }
        if (Device.getDevicesList() == null || Device.getDevicesList().size() == 0) {
            String queryString = "{ \"realm\": { \"name\": \"master\" }}";
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(queryString);
            JsonObject query = jsonElement.getAsJsonObject();
            APIManager.queryDevices(query);
        }
        if (Map.getMapObj() == null) {
            APIManager.getMap();
        }
        if (Datapoint.getDatapointList() == null) {
            String queryString = "{ \"fromTimestamp\": 1699056771451, \"toTimestamp\": 1701698167187, \"fromTime\": \"2023-11-03T07:13:07.945Z\", \"toTime\": \"2023-12-05T07:13:07.945Z\", \"type\": \"string\" }";
            JsonParser jsonParser = new JsonParser();
            JsonObject query = jsonParser.parse(queryString).getAsJsonObject();
            APIManager.getDataPoint("5zI6XqkQVSfdgOrZ1MyWEf","rainfall",query);
        }
        return "done";
    }

    @Override
    protected void onProgressUpdate(Long... values){
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String res){
//        if (pdWaiting.isShowing()){
//            pdWaiting.dismiss();
//        }
        super.onPostExecute(res);
    }
}
