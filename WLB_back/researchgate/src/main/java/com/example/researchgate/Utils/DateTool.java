package com.example.researchgate.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTool {
    public static String getCurDate(){
        Date time = new Date();
        // hh:12h HH:24h
        String strDateFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(time);
    }
    public static String getCurTime(){
        Date time = new Date();
        // hh:12h HH:24h
        String strDateFormat = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(time);
    }
    public static Date string2Date(String string, int format){
//        string = string.replace("T", " ");
//        string = string.replace("Z", "UTC+8");
        // 模式0
        SimpleDateFormat sdf_day = new SimpleDateFormat("yyyy/MM/dd");
        sdf_day.setTimeZone(TimeZone.getTimeZone("GMT+:08:00"));
        // 模式1
        SimpleDateFormat sdf_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf_time.setTimeZone(TimeZone.getTimeZone("GMT+:08:00"));
        Date res = null;
        if (format == 0){
            try {
                res = sdf_day.parse(string);
            } catch (ParseException e) {
                System.out.println(e.toString());
            }
        }else{
            try {
                res = sdf_time.parse(string);
            } catch (ParseException e) {
                System.out.println(e.toString());
            }
        }
        return res;
    }

    public static boolean isXDaysAgo(String date, long days) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long gapOfDays = 0;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));//现在系统当前时间
            Date pastTime = dateFormat.parse(date);//过去时间
            long diff = currentTime.getTime() - pastTime.getTime();
            if(days == 0){
                return diff > 0;
            }
            gapOfDays = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        return gapOfDays >= days;
    }

    public static String long2String(long time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(new Date(time));
    }
}
