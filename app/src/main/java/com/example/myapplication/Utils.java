package com.example.myapplication;

import android.content.Context;
import android.os.Handler;

import com.example.myapplication.Model.Datapoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    public static Handler delayHandler;

    // Format camel case back to normal string
    public static String formatString(String s) {
        char first = Character.toUpperCase(s.charAt(0));
        return first + s.substring(1).replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    // Convert dp to px
    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
    public static long convertTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getDefault());

        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
    public static long convertHourTime(String time) {
//        2017-02-08 00:55:29
        //03:48:33 04-05-2017
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getDefault());

        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getDateNow() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String time = df.format(c.getTime());
        return time;
    }

//    public static long formatStringToLong(String stringUTCDate) {
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm dd/MM/yyyy");
//        df.setTimeZone(TimeZone.getTimeZone("UTC"));
//        df.toLocalizedPattern();     // lấy giờ theo vị trí
//        try {
//            Date time = df.parse(stringUTCDate);
//            return (time.getTime() / 1000);
//        } catch (ParseException e) {
//            return 0;
//        }
//    }

    public static String formatLongToDate(long timeLong) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeLong);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM");
        return df.format(c.getTime());
    }
    public static String formatLongToMonth(long timeLong) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeLong);
        SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
        return df.format(c.getTime());
    }
    public static String formatLongToYear(long timeLong) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeLong);
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return df.format(c.getTime());
    }

    public static String formatLongToHour(long timeLong) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeLong);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(c.getTime());
    }

    public static String formatLongToDateHour(long timeLong) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeLong);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(c.getTime());
    }

}