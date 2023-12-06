package com.example.myapplication;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Datapoint;
import com.example.myapplication.Model.Token;
import com.example.myapplication.RestAPI.APIInterface;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartFragment extends Fragment {
    private HomeActivity parentActivity;
    private View mView;
    private Context context;
    int year, month, day;
    AutoCompleteTextView auto_attr, auto_tf;
    TextInputEditText pickdate;
    ArrayAdapter<String> adapter_attribute, adapter_timeframe;
    private Button btn_show;
    private String[] attributes = {"Humidity (%)", "Rainfall (mm)", "Temperature (°C)", "Wind speed (km/h)"};
    private String[] timeframes = {"Day", "Month", "Year"};
    private String attribute, timeframe;
    private DatePickerDialog datePickerDialog;
    List<Datapoint> datapointList;

    private LineChart chart;
    private String id,datetime = null,monthtime=null;

    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_chart, container, false);
        parentActivity = (HomeActivity) getActivity();

        dropdown_menu(mView);
        pickDate(mView);

        //
        Initial(mView);
        return mView;
    }

    private void dropdown_menu(View mView) {
        auto_attr = mView.findViewById(R.id.drv_attribute);
        auto_tf = mView.findViewById(R.id.drv_timeframe);

        adapter_attribute = new ArrayAdapter<String>(context, R.layout.item_dropdown, attributes);
        auto_attr.setAdapter(adapter_attribute);

        adapter_timeframe = new ArrayAdapter<String>(context, R.layout.item_dropdown, timeframes);
        auto_tf.setAdapter(adapter_timeframe);

        auto_attr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                attribute = parent.getItemAtPosition(position).toString();
            }
        });
        auto_tf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeframe = parent.getItemAtPosition(position).toString();
            }
        });
    }

    private void pickDate(View mView) {
        pickdate = mView.findViewById(R.id.txt_endingday);

        final Calendar calendar = Calendar.getInstance();

        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        month = i1 +1;
                        day = i2;
                        year = i;
                        datetime = day + "/" + month + "/" + year;
                        monthtime = "01" + "/" + month + "/" + year;
                        pickdate.setText(datetime);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


    }
    private void timeframe_process(List<Datapoint> listdatapoint, String[] mDays, float[] mData) {
        if (timeframe.equals("Day")) {
            for (int i = 0; i < listdatapoint.size(); i++) {
                mDays[i] = Utils.formatLongToHour(listdatapoint.get(i).getTimestamp());
                mData[i] = listdatapoint.get(i).getValue();
            }
        } else if(timeframe.equals("Month")) {
            for (int i = 0; i < listdatapoint.size(); i++) {
                mDays[i] = Utils.formatLongToDate(listdatapoint.get(i).getTimestamp());
                mData[i] = listdatapoint.get(i).getValue();
            }
        } else if(timeframe.equals("Year")) {
            for (int i = 0; i < listdatapoint.size(); i++) {
                mDays[i] = Utils.formatLongToMonth(listdatapoint.get(i).getTimestamp());
                mData[i] = listdatapoint.get(i).getValue();
            }
        } else {
            Toast.makeText(context, "No timeframes are selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void process_endingDate(List<Datapoint> listdatapoint,List<Datapoint> datapoints,String datetime, String timeframe){
        long timestamp = 1701848814712L;
        long timesmonth = 1698835169710L;
        String hourtime = "23:59:59 " + datetime;
        long timesday = 1698796800000L;
        if (datetime==null){
            Toast.makeText(context, "Pick date ending", Toast.LENGTH_SHORT).show();
        } else {
            timestamp = Utils.convertHourTime(hourtime);
            timesmonth = Utils.convertTime(monthtime);
            timesday = Utils.convertHourTime(hourtime);
        }
        for (int i = datapoints.size() - 1; i >= 0; i--) {
            long timestamppoint = datapoints.get(i).getTimestamp();
            if (timestamppoint <= timestamp) {
                listdatapoint.add(new Datapoint(datapoints.get(i).getTimestamp(), datapoints.get(i).getValue()));
            } else {
                break;
            }
        }
//        if(timeframe.equals("Year")) {
//            for (int i = datapoints.size() - 1; i >= 0; i--) {
//                long timestamppoint = datapoints.get(i).getTimestamp();
//                if (timestamppoint <= timestamp) {
//                    listdatapoint.add(new Datapoint(datapoints.get(i).getTimestamp(), datapoints.get(i).getValue()));
//                } else {
//                    break;
//                }
//            }
//        } else if(timeframe.equals("Month")) {
//            for (int i = datapoints.size() - 1; i >= 0; i--) {
//                long timestamppoint = datapoints.get(i).getTimestamp();
//                if ((timestamppoint >= timesmonth) && (timestamppoint<= timestamp)) {
//                    listdatapoint.add(new Datapoint(datapoints.get(i).getTimestamp(), datapoints.get(i).getValue()));
//                } else {
//                    break;
//                }
//            }
//        } else if (timeframe.equals("Day")) {
//            for (int i = datapoints.size() - 1; i >= 0; i--) {
//                long timestamppoint = datapoints.get(i).getTimestamp();
//                if ((timestamppoint >= timesday) && (timestamppoint<= timestamp)) {
//                    listdatapoint.add(new Datapoint(datapoints.get(i).getTimestamp(), datapoints.get(i).getValue()));
//                } else {
//                    break;
//                }
//            }
//        }
    }

    private void Initial(View view) {
        btn_show = view.findViewById(R.id.btnShow);
        chart = view.findViewById(R.id.chart1);

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(attribute.equals("Rainfall (mm)")) {
                    List<Datapoint> datapoints = Datapoint.getDatapointRainfallList();
//                    if(datapointList != null) {
//                        datapointList = null;
//                        datapointList = new ArrayList<>();
//                    } else {
//                        datapointList= new ArrayList<>();
//                    }
                    List<Datapoint> dataRain = new ArrayList<>();
                    process_endingDate(dataRain,datapoints, datetime, timeframe);
                    String[] mDays = new String[dataRain.size()];
                    float[] mData = new float[dataRain.size()];

                    timeframe_process(dataRain,mDays,mData);

                    setUpChart(chart, mDays, mData, attribute,timeframe);
                } else if (attribute.equals("Humidity (%)")) {
                    List<Datapoint> datapoints = Datapoint.getDatapointHumidityList();
                    if(datapointList != null) {
                        datapointList = null;
                        datapointList = new ArrayList<>();
                    } else {
                        datapointList= new ArrayList<>();
                    }
                    process_endingDate(datapointList,datapoints,datetime,timeframe);
                    String[] mDays = new String[datapointList.size()];
                    float[] mData = new float[datapointList.size()];

                    timeframe_process(datapointList,mDays,mData);

                    setUpChart(chart, mDays, mData, attribute,timeframe);
                } else if (attribute.equals("Temperature (°C)")) {
                    List<Datapoint> datapoints = Datapoint.getDatapointTemperatureList();
//                    if(datapointList != null) {
//                        datapointList = null;
//                        datapointList = new ArrayList<>();
//                    } else {
//                        datapointList= new ArrayList<>();
//                    }
                    List<Datapoint> dataTemp = new ArrayList<>();
                    process_endingDate(dataTemp,datapoints,datetime,timeframe);

                    String[] mDays = new String[dataTemp.size()];
                    float[] mData = new float[dataTemp.size()];

                    timeframe_process(dataTemp,mDays,mData);

                    setUpChart(chart, mDays, mData, attribute,timeframe);
                } else if (attribute.equals("Wind speed (km/h)")) {
                    List<Datapoint> datapoints = Datapoint.getDatapointWindspeedList();
                    if(datapointList != null) {
                        datapointList = null;
                        datapointList = new ArrayList<>();
                    } else {
                        datapointList= new ArrayList<>();
                    }
                    process_endingDate(datapointList,datapoints,datetime,timeframe);

                    String[] mDays = new String[datapointList.size()];
                    float[] mData = new float[datapointList.size()];

                    timeframe_process(datapointList,mDays,mData);

                    setUpChart(chart, mDays, mData, attribute,timeframe);
                } else {
                    Toast.makeText(context, "No attributes are selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setUpChart(LineChart chart, String[] mDays,float[] mData, String attribute, String timeframe) {

        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // đưa trục X xuống dưới
        xAxis.setDrawGridLines(false);      //bỏ kẻ ô
        //Khoảng cách của các điểm x
        if(timeframe.equals("Day")) {
            xAxis.setGranularity(2f);
        } else if(timeframe.equals("Month")) {
            xAxis.setGranularity(2f);
        } else if(timeframe.equals("Year")) {
            xAxis.setGranularity(5f);
        }
        xAxis.setLabelCount(mData.length);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(mDays));

        YAxis yAxisRight = chart.getAxisRight();
        YAxis yAxisLeft = chart.getAxisLeft();
//        yAxisRight.setEnabled(false);      // bỏ trục Y bên phải
        xAxis.setAxisLineWidth(2f);     // độ dày của trục X,Y
        yAxisLeft.setAxisLineWidth(2f);
//        yAxisLeft.setGranularity(1f); //khoảng cach giua các diem Y
        xAxis.setAxisLineColor(R.color.dark_greenlv1); // Màu của đường trục X
        yAxisLeft.setAxisLineColor(R.color.dark_greenlv1);

        ArrayList<Entry> barEntries = new ArrayList<>();

        for (int j = 0; j < mData.length; j++) {
            barEntries.add(new BarEntry(j, mData[j]));
        }
        LineDataSet barDataSet = new LineDataSet(barEntries, attribute );
        barDataSet.setDrawIcons(false);
        barDataSet.setLineWidth(4f);
        barDataSet.setCircleRadius(4f);
        barDataSet.setColors(ContextCompat.getColor(context, R.color.green)); //set color cho line
        barDataSet.setCircleColor(ContextCompat.getColor(context, R.color.bg1)); // set color cho điểm
        barDataSet.setDrawFilled(false); // Bật fill vùng
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(barDataSet);
        LineData data = new LineData(dataSets);
        chart.setData(data);
        // hien thi so du lieu tren bieu do
        chart.setVisibleXRangeMaximum(10);
        chart.getXAxis().setSpaceMax(1);
        chart.setEnabled(false);
        chart.setBackgroundColor(ContextCompat.getColor(context, R.color.yellowchart));
        chart.invalidate();
    }

}