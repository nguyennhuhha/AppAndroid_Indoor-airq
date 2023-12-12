package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import com.example.myapplication.Model.Datapoint;
import com.example.myapplication.Model.Device;
import com.example.myapplication.Model.Map;
import com.example.myapplication.Model.User;
import com.example.myapplication.API.APIManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.Instant;
import java.util.Calendar;
import java.time.Clock;


public class AsyncTasks extends AsyncTask<String, Long, String> {
    private Context context;
    public AsyncTasks(Context context){
        this.context = context;
    }
    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
        //use UI thread here
    }
    @Override
    protected  String doInBackground(String... params){
        //Call API
        if (User.getMe() == null) {
            APIManager.getUserInfo();
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
        if (Datapoint.getDatapointRainfallList() == null) {
//            String queryString = "{ \"fromTimestamp\": 1698834342208, \"toTimestamp\": 1701771942208, \"fromTime\": \"2023-11-01T07:13:07.945Z\", \"toTime\": \"2023-12-05T10:25:42.208Z\", \"type\": \"string\" }";
            long time = Calendar.getInstance().getTimeInMillis();
            String datetime = Utils.formatLongToDateHour(time);
            String queryString = "{ \"fromTimestamp\": 1698834342208, \"toTimestamp\": REPLACE_TIMESTAMP, \"fromTime\": \"2023-11-01T07:13:07.945Z\", \"toTime\": \"REPLACE_DATETIME\", \"type\": \"string\" }";
            String replacedQueryString = queryString.replace("REPLACE_TIMESTAMP", String.valueOf(time));
            replacedQueryString = replacedQueryString.replace("REPLACE_DATETIME", datetime);
            JsonParser jsonParser = new JsonParser();
            JsonObject query = jsonParser.parse(replacedQueryString).getAsJsonObject();
            APIManager.getDataPointRainfall("5zI6XqkQVSfdgOrZ1MyWEf","rainfall",query);
        }
        if (Datapoint.getDatapointTemperatureList() == null) {
            long time = Calendar.getInstance().getTimeInMillis();
            String datetime = Utils.formatLongToDateHour(time);
            String queryString = "{ \"fromTimestamp\": 1698834342208, \"toTimestamp\": REPLACE_TIMESTAMP, \"fromTime\": \"2023-11-01T07:13:07.945Z\", \"toTime\": \"REPLACE_DATETIME\", \"type\": \"string\" }";
            String replacedQueryString = queryString.replace("REPLACE_TIMESTAMP", String.valueOf(time));
            replacedQueryString = replacedQueryString.replace("REPLACE_DATETIME", datetime);
            JsonParser jsonParser = new JsonParser();
            JsonObject query = jsonParser.parse(replacedQueryString).getAsJsonObject();
            APIManager.getDataPointTemperature("5zI6XqkQVSfdgOrZ1MyWEf","temperature",query);
        }
        if (Datapoint.getDatapointHumidityList() == null) {
            long time = Calendar.getInstance().getTimeInMillis();
            String datetime = Utils.formatLongToDateHour(time);
            String queryString = "{ \"fromTimestamp\": 1698834342208, \"toTimestamp\": REPLACE_TIMESTAMP, \"fromTime\": \"2023-11-01T07:13:07.945Z\", \"toTime\": \"REPLACE_DATETIME\", \"type\": \"string\" }";
            String replacedQueryString = queryString.replace("REPLACE_TIMESTAMP", String.valueOf(time));
            replacedQueryString = replacedQueryString.replace("REPLACE_DATETIME", datetime);
            JsonParser jsonParser = new JsonParser();
            JsonObject query = jsonParser.parse(replacedQueryString).getAsJsonObject();
            APIManager.getDataPointHumidity("5zI6XqkQVSfdgOrZ1MyWEf","humidity",query);
        }
        if (Datapoint.getDatapointWindspeedList() == null) {
            long time = Calendar.getInstance().getTimeInMillis();
            String datetime = Utils.formatLongToDateHour(time);
            String queryString = "{ \"fromTimestamp\": 1698834342208, \"toTimestamp\": REPLACE_TIMESTAMP, \"fromTime\": \"2023-11-01T07:13:07.945Z\", \"toTime\": \"REPLACE_DATETIME\", \"type\": \"string\" }";
            String replacedQueryString = queryString.replace("REPLACE_TIMESTAMP", String.valueOf(time));
            replacedQueryString = replacedQueryString.replace("REPLACE_DATETIME", datetime);
            JsonParser jsonParser = new JsonParser();
            JsonObject query = jsonParser.parse(replacedQueryString).getAsJsonObject();
            APIManager.getDataPointWindSpeed("5zI6XqkQVSfdgOrZ1MyWEf","windSpeed",query);
        }
        return "done";
    }

    @Override
    protected void onProgressUpdate(Long... values){
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String res){
        super.onPostExecute(res);
    }
}
